package com.tymall.ui.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.db.CacheManager;
import com.tymall.R;
import com.tymall.ui.MainActivity;
import com.tymall.ui.adapter.TabFragmentAdapter;
import com.tymall.app.Constant;
import com.tymall.base.XBaseActivity;
import com.tymall.callback.LogAndRegCallback;
import com.tymall.data.AppDataManager;
import com.tymall.ui.fragment.FragmentLogin;
import com.tymall.ui.fragment.FragmentRegister;
import com.tymall.utils.GlideCacheUtil;
import com.tymall.utils.SP;
import com.tymall.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoginActivity extends XBaseActivity implements LogAndRegCallback {
    public static LoginActivity loginActivity;
    @BindView(R.id.login_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.login_viewpager)
    ViewPager viewPager;

    private TabFragmentAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private ViewHolder holder;
    private SharedPreferencesUtils share;
    private String userId;
    private String from;

    @Override
    protected void initView() {
        if(SP.get(Constant.LOGIN_FROM_MARKEKT,false)) {
            SP.set(Constant.MALL_IS_ALREADY_LOGIN, true);
        }

        if(SP.get(Constant.LOGIN_FROM_MON,false)) {
            SP.set(Constant.MON_IS_ALREADY_LOGIN, true);
        }
        loginActivity = this;
        share = SharedPreferencesUtils.getInstance();
        userId = share.getShared("userId");

        tabs.add(cnt.getResources().getString(R.string.login));
        tabs.add(cnt.getResources().getString(R.string.register));
        fragments.add(new FragmentLogin());
        fragments.add(new FragmentRegister());
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        setTabView();

    }

    /**
     * 设置Tab的样式
     */
    private void setTabView() {
        holder = null;
        for (int i = 0; i < tabs.size(); i++) {
            //依次获取标签
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.tab_item);
            holder = new ViewHolder(tab.getCustomView());
            // 为标签填充数据
            holder.tvTabName.setText(tabs.get(i));
            //默认选择第一项
            if (i == 0) {
                holder.tvTabName.setSelected(true);
                holder.tvTabName.setTextSize(26);
                holder.tvTabName.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }
        //tab选中的监听事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setTextAppearance(LoginActivity.this, R.style.TabLayoutTextStyle);
//                holder.tvTabName.setSelected(true);
//                //选中后字体变大
//                holder.tvTabName.setTextSize(26);
//
//                //让Viewpager跟随TabLayout的标签切换
//                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setTextAppearance(LoginActivity.this, R.style.TabLayoutTextStyle2);
//                holder.tvTabName.setSelected(false);
//                //恢复为默认字体大小
//                holder.tvTabName.setTextSize(16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onLoginCallBack(String uid, String uname, String token) {
        share.addShared("userId", uid);
        share.addShared("userName", uname);
        share.addShared("shareName", uname);
        share.addShared("token", token);
        share.addShared("userPhoto", "1");
        SP.set("loginState",true);
        try {
            AppDataManager.getInstance().cleanUserPhone();
            GlideCacheUtil.getInstance().clearImageAllCache(LoginActivity.this);
            CacheManager.getInstance().clear();
            SP.set(Constant.GOODS_LIST_CACHE, "");
            String cachekey = SP.get(Constant.MON_ZONE_ID, "");
            SP.set(cachekey, "");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(SP.get(Constant.LOGIN_FROM_MARKEKT,false)) {
            SP.set(Constant.MALL_NEED_SKIP_MALL,true);
            SP.set(Constant.LOGIN_FROM_MARKEKT,false);
            SP.set(Constant.MALL_IS_ALREADY_LOGIN,false);
            SP.set("loginState",false);


        }
        if(SP.get(Constant.LOGIN_FROM_MON,false)) {
            SP.set(Constant.MON_NEED_SKIP_MON,true);
            SP.set(Constant.LOGIN_FROM_MON,false);
            SP.set(Constant.MON_IS_ALREADY_LOGIN,false);
            SP.set("loginState",false);


        }
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterCallBack() {
        viewPager.setCurrentItem(1);
    }

    class ViewHolder {
        TextView tvTabName;

        public ViewHolder(View tabView) {
            tvTabName = tabView.findViewById(R.id.tv_tab_name);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
