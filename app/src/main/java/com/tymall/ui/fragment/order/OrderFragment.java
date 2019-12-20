package com.tymall.ui.fragment.order;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.barlibrary.ImmersionBar;
import com.tymall.R;
import com.tymall.ui.adapter.MyShowPagerAdapter;
import com.tymall.base.XBaseFragment;
import com.tymall.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderFragment extends XBaseFragment {


    @BindView(R.id.titleBar)
    TitleBarView titleBar;


    @BindView(R.id.page_tabs)
    PagerSlidingTabStrip pageTabs;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<Fragment> fragmentList;
    private MyShowPagerAdapter myShowPagerAdapter;


    /**
     * 全部订单
     */
    private AllOrderFragment allOrderFragment;

    /**
     * 未付款
     */
    private UnpaidFragment unpaidFragment;

    /**
     * 待发货
     */
    private ToBeDeliveredFragment toBeDeliveredFragment;

    /**
     * 待收货
     */
    private PendingReceiptFragment pendingReceiptFragment;

    /**
     * 已完成
     */
    private CompletedFragment completedFragment;

    /**
     * 已取消
     */

    private CancelledFragment cancelledFragment;


    @Override
    protected void initView() {

        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        allOrderFragment = new AllOrderFragment();
        unpaidFragment = new UnpaidFragment();
        toBeDeliveredFragment = new ToBeDeliveredFragment();
        pendingReceiptFragment = new PendingReceiptFragment();
        completedFragment = new CompletedFragment();
        cancelledFragment = new CancelledFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(allOrderFragment);
        fragmentList.add(unpaidFragment);
        fragmentList.add(toBeDeliveredFragment);
        fragmentList.add(pendingReceiptFragment);
        fragmentList.add(completedFragment);
        fragmentList.add(cancelledFragment);


        myShowPagerAdapter = new MyShowPagerAdapter(getActivity().getSupportFragmentManager(), new String[]{cnt.getResources().getString(R.string.quan_bu), cnt.getResources().getString(R.string.wei_fu_kuan), cnt.getResources().getString(R.string.dai_fa_huo), cnt.getResources().getString(R.string.dai_shou_huo), cnt.getResources().getString(R.string.yi_wan_cheng), cnt.getResources().getString(R.string.yi_qu_xiao)}, fragmentList);
        viewPager.setAdapter(myShowPagerAdapter);
        pageTabs.setViewPager(viewPager);
        pageTabs.setTabPadding(5);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0://全部订单
                        allOrderFragment.setRefreshOrderList();
                        break;
                    case 1://未付款
                        unpaidFragment.setRefreshOrderList();
                        break;
                    case 2://待发货
                        toBeDeliveredFragment.setRefreshOrderList();
                        break;
                    case 3://待收货
                        pendingReceiptFragment.setRefreshOrderList();
                        break;
                    case 4://已完成
                        completedFragment.setRefreshOrderList();
                        break;
                    case 5://已取消
                        cancelledFragment.setRefreshOrderList();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.navigation_bar_color)
                .init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dbmall_order;
    }


}
