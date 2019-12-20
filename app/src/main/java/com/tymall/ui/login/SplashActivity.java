package com.tymall.ui.login;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.ui.MainActivity;
import com.tymall.utils.CacheUtils;
import com.tymall.utils.SP;
import com.tymall.utils.SharedPreferencesUtils;

public class SplashActivity extends AppCompatActivity {


    private String userId;

    private ImageView ivWelcomeSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ImmersionBar.with(this).init();
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        userId = SharedPreferencesUtils.getInstance().getShared("userId");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                start();
            }
        }, 1000);
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化 默认白底黑字
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.navigation_bar_color)
                .init();


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

    public void start() {
        try {


            int currentCode = CacheUtils.getAppVersionCode(SplashActivity.this);
            int oldCode = SP.get(Constant.OLD_VERSION, 0);

            /**
             *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
             */
            if (oldCode == 0) {
                Intent intent1 = new Intent();
                intent1.setClass(SplashActivity.this, IntroduceActivity.class);
                startActivity(intent1);
            } else {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
            SP.set(Constant.OLD_VERSION, currentCode);
            overridePendingTransition(R.anim.nothing, R.anim.nothing);
            finish();

        } catch (Exception e) {

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}