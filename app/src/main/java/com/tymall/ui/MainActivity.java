package com.tymall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.base.XBaseActivity;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.event.EosEvent;
import com.tymall.ui.fragment.DBMallHomeFragment;
import com.tymall.ui.fragment.MyCashBackFragment;
import com.tymall.ui.fragment.ShopFragment;
import com.tymall.ui.fragment.order.OrderFragment;
import com.tymall.utils.LogUtil;
import com.tymall.utils.SP;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import q.rorbin.badgeview.Badge;


/**
 * Created by wds on 2019/4/22.
 * mall商城页面
 */

public class MainActivity extends XBaseActivity {
    private static final String TAG = "DBMallActivity";
    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    @BindView(R.id.activity_with_view_pager)
    RelativeLayout activityWithViewPager;

    private static final String KEY_MAINPAGE_TAG = "mainpage_tag";
    private static final String TAB_DBMALL = "dbmall";
    private static final String TAB_SHOP = "shop";
    private static final String TAB_ORDER = "order";
    private static final String TAB_MY_CASH = "my_cash";
    private Fragment curFragment;
    private String curTab;
    private String targetTab = TAB_DBMALL;
    private Badge badge;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbmall;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        initFragment(savedInstanceState);
//        badge = new QBadgeView(this)
//                .setBadgeNumber(-1)
//                .setGravityOffset(12, 2, true)
//                .bindTarget(bnve.getBottomNavigationItemView(1))
//                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                    @Override
//                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                    }
//                });

    }



    @Override
    protected void onResume() {
        super.onResume();
        int skipMode = SP.get(Constant.SKIP_MODE, -1);
        SP.set(Constant.SKIP_MODE_NEED_FINISH,false);
        if (skipMode > 100 && skipMode < 200) {
            switch (skipMode) {
                case 101:
                    bnve.getMenu().getItem(0).setChecked(true);
                    targetTab = TAB_DBMALL;
                    switchFragment(TAB_DBMALL);
                    break;
                case 102:
                    bnve.getMenu().getItem(1).setChecked(true);
                    targetTab = TAB_SHOP;
                    switchFragment(TAB_SHOP);
                    break;
                case 103:
                    bnve.getMenu().getItem(2).setChecked(true);
                    targetTab = TAB_DBMALL;
                    switchFragment(TAB_ORDER);
                    break;
                case 104:
                    bnve.getMenu().getItem(3).setChecked(true);
                    targetTab = TAB_DBMALL;
                    switchFragment(TAB_MY_CASH);
                    break;
                default:
                    break;

            }
            SP.set(Constant.SKIP_MODE, -1);
        }

       // queryCartCount();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntentParams(intent);
    }


    @Override
    protected void initView() {

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_dbmall:
                        targetTab = TAB_DBMALL;
                        switchFragment(TAB_DBMALL);
                        return true;
                    case R.id.tab_shop:
                        targetTab = TAB_SHOP;
                        switchFragment(TAB_SHOP);
                        return true;
                    case R.id.tab_order:
                        targetTab = TAB_ORDER;
                        switchFragment(TAB_ORDER);
                        return true;
                    case R.id.tab_my_cash:
                        targetTab = TAB_MY_CASH;
                        switchFragment(TAB_MY_CASH);

                        return true;

                }

                targetTab = TAB_DBMALL;
                return false;
            }
        });
    }

    private void checkIntentParams(Intent intent) {


    }


    public void queryCartCount() {


        ServerApi.getInstance().queryCartCount(getContext())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.optInt("errno");
                            int data = jsonObject.optInt("data");
                            if (code == 0) {
                                addBadgeAt(data);

                            }
                        } catch (Exception e) {

                        }

                    }


                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        ToastUtils.show(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("dbmall", "onSaveInstanceState: curTab = " + curTab);
        outState.putString(KEY_MAINPAGE_TAG, curTab);
    }


    private void initFragment(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (savedInstanceState == null) {
            Fragment homeFragment = new DBMallHomeFragment();
            curFragment = homeFragment;
            curTab = TAB_DBMALL;
            ft.add(R.id.layout_content, curFragment, TAB_DBMALL).show(curFragment).commitAllowingStateLoss();
        } else {
            String preTab = savedInstanceState.getString(KEY_MAINPAGE_TAG);
            LogUtil.d(TAG, "initFragment: preTab = " + preTab);
            if (preTab == null) {
                preTab = TAB_DBMALL;
            }
            curTab = preTab;
            String[] tags = new String[]{TAB_DBMALL, TAB_SHOP, TAB_ORDER, TAB_MY_CASH};
            boolean hasFoundPreFragment = false;
            for (String tag : tags) {
                Fragment fragment = fm.findFragmentByTag(tag);
                if (fragment != null) {
                    if (tag.equals(preTab)) {
                        ft.show(fragment);
                        curFragment = fragment;
                        hasFoundPreFragment = true;
                    } else {
                        ft.hide(fragment);
                    }
                }
            }
            if (!hasFoundPreFragment) {
                curFragment = createFragmentByTab(preTab);
                ft.add(R.id.layout_content, curFragment, preTab).show(curFragment).commitAllowingStateLoss();
            }
        }

    }


    private Fragment createFragmentByTab(String tab) {
        switch (tab) {
            case TAB_DBMALL:
                return new DBMallHomeFragment();
            case TAB_SHOP:
                return new ShopFragment();
            case TAB_ORDER:
                return new OrderFragment();
            case TAB_MY_CASH:
                return new MyCashBackFragment();
            default:
                throw new IllegalArgumentException("error tab: " + tab);
        }
    }


    private void switchFragment(String tabName) {


        curTab = tabName;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag(tabName) != null) {
            if (fm.findFragmentByTag(tabName) == curFragment) {
                return;
            }
            if (curFragment != null) {
                ft.hide(curFragment);
            }
            curFragment = fm.findFragmentByTag(tabName);
            ft.show(fm.findFragmentByTag(tabName)).commitAllowingStateLoss();
        } else {
            if (curFragment != null) {
                ft.hide(curFragment);
            }
            curFragment = createFragmentByTab(tabName);
            ft.add(R.id.layout_content, curFragment, tabName).show(curFragment).commitAllowingStateLoss();
        }
    }


    private Badge addBadgeAt(int number) {

        badge.setBadgeNumber(number);
        return badge;
    }


    @Subscribe
    public void onEvent(EosEvent event) {


    }


    @Override
    protected void onStop() {


        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    private long mExitTime;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - mExitTime > 2000) {
            XToastUtils.showRoundRectToast(getString(R.string.string_click_quit));
            mExitTime = secondTime;
        } else {
            finish();
        }
    }


}




