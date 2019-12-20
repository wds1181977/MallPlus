package com.tymall.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.db.CacheManager;
import com.tymall.R;
import com.tymall.utils.CustomDialog;
import com.tymall.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * XBaseAcitivity全局沉浸式
 * 请在immersionBarEnabled方法中实现你的沉浸式代码，
 * @author wds
 * @date 2019 /3/26
 */

public abstract class XBaseActivity extends BaseActivity {
    private Unbinder unbinder;
    public static XBaseActivity activity;
    public Context cnt;
    //  protected abstract void setTitleBar();

    protected TitleBarView titleBar;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected ConstraintLayout toolBar_left;

    protected ProgressBar progressBar;
    protected int type = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        activity = this;
        cnt = this;
        unbinder = ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        PushAgent.getInstance(cnt).onAppStart();
        initTitle();
        initToolBar();
        initView();

    }

    public void showProgressBarView() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBarView() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化 默认白底黑字
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
        progressBar = findViewById(R.id.progressbar);
        if (titleBar == null) {
            return;
        }
        type = titleBar.getStatusBarModeType();
        //无法设置白底黑字
        if (type <= 0) {
            //5.0 半透明模式alpha-102
            titleBar.setStatusAlpha(0);
        }
        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    protected void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        ImmersionBar.setTitleBar(this, toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
      //  toolBar_left = findViewById(R.id.toolbar_back);

        if (toolBar_left == null) {
            return;
        }
        toolBar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void setToolbarTitle(String title){
        if(toolbarTitle == null || TextUtils.isEmpty(title)){
            return;
        }
        toolbarTitle.setText(title);
    }

    public void setTitleLine(boolean enable) {
        titleBar.setDividerVisible(enable);
    }

    public void setTitleBarSubText(String title){
        if (titleBar == null) {
            return;
        }
        titleBar.setTitleSubText(title);
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this
                    .getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activity != null) {
            activity = null;
        }
        if(unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); //统计时长
        CustomDialog.getInstance(this).destoryDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); //统计时长
    }


    public boolean isHasCache(String httpUrl) {
        try {
            if (CacheManager.getInstance().
                    get(httpUrl +
                            SharedPreferencesUtils.getInstance().getShared("userId")) != null) {
                return true;

            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
