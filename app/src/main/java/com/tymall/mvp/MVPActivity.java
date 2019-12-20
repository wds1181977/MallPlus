package com.tymall.mvp;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.barlibrary.ImmersionBar;
import com.tymall.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wpy on 2017/7/22.
 */

/**
 * Base类的基类
 *
 * @param <V>
 * @param <P>
 */
public abstract class MVPActivity<V extends GEMUI, P extends ActPresenter<V>> extends BaseCoreActivity {
    private P presenter;

    private V ui;
    private Unbinder unbinder;
    public Context cnt;
    TitleBarView  titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = createUI();

        setContentView(ui.getContentLayout());
        cnt= this;
        unbinder = ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        presenter = createPresenter();
        presenter.onUIReady(this, getUI());
        presenter.onCreate(savedInstanceState);
        initTitle();
        initViews();

    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.navigation_bar_color)
                .init();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
            // 非必加
            // 如果你的app可以横竖屏切换，适配了4.4或者华为emui3.1系统手机，并且navigationBarWithKitkatEnable为true，
            // 请务必在onConfigurationChanged方法里添加如下代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、android4.4或者华为emui3.1系统手机；3、navigationBarWithKitkatEnable为true）
            // 否则请忽略
            ImmersionBar.with(this).init();
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }



    protected void initTitle() {
        titleBar = findViewById(R.id.titleBar);
        if (titleBar == null) {
            return;
        }

        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void setTitleBarSubText(String title){
        if (titleBar == null) {
            return;
        }
        titleBar.setTitleSubText(title);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getPresenter().onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        getPresenter().onDestroy();
        unbinder.unbind();
        super.onDestroy();
    }

    public abstract void hideStatus();

    protected abstract V createUI();

    protected abstract P createPresenter();

    public V getUI() {
        return ui;
    }

    public P getPresenter() {
        return presenter;
    }

    protected abstract void initViews();
}
