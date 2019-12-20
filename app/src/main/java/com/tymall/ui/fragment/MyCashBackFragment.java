package com.tymall.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tymall.R;
import com.tymall.ui.adapter.MyCashBackAdapter;
import com.tymall.base.XBaseFragment;
import com.tymall.model.MyCashBackModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.ServerApi;

import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.CloseMinePoolDialog;
import com.tymall.view.DialogOnClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MyCashBackFragment extends XBaseFragment implements OnRefreshListener, OnLoadMoreListener {

    /**
     * 待返现DBM
     */
    @BindView(R.id.tv_waiting_for_dbm)
    TextView tvWaitingForDbm;


    /**
     * 已返现DBM
     */
    @BindView(R.id.tv_cash_back_dbm)
    TextView tvCashBackDbm;

    /**
     * 已返现积分
     */
    @BindView(R.id.tv_cash_back_integral)
    TextView tvCashBackIntegral;

    /**
     * 积分余额
     */
    @BindView(R.id.tv_point_balance)
    TextView tvPointBalance;

    @BindView(R.id.rl_recycleview)
    RecyclerView rlRecycleview;
    private MyCashBackAdapter adapter;

    @BindView(R.id.titleBar)
    TitleBarView titleBar;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    @BindView(R.id.rl_today_link_back)
    RelativeLayout rlTodayLinkBack;

    @BindView(R.id.tv_today_link_back)
    TextView tvTodayLinkBack;


    private List<MyCashBackModel> cashBackList;

    private String unspentPoints = "";

    /**
     * 返现说明
     */
    private String ruleInfo = "";

    private int curragePage;


    @Override
    protected void initView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        cashBackList = new ArrayList<>();
        titleBar.setStatusBarLightMode(false)
                .setTitleMainTextMarquee(true);

        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCashbackInstructionsDialog();
            }
        });
        rlRecycleview.setHasFixedSize(true);
        rlRecycleview.setLayoutManager(new LinearLayoutManager(cnt));
        adapter = new MyCashBackAdapter(cnt, cashBackList);
        rlRecycleview.setAdapter(adapter);
        curragePage = 1;
        pointsAccount();
        pointsAccountLogs();
    }


    /**
     * 返现说明弹窗
     */
    private void showCashbackInstructionsDialog() {
        CloseMinePoolDialog closeMinePoolDialog = new CloseMinePoolDialog(cnt);
        closeMinePoolDialog.setTitle(cnt.getResources().getString(R.string.cashback_instructions));
        closeMinePoolDialog.setContent(ruleInfo);
        closeMinePoolDialog.setButtonYesContent(cnt.getResources().getString(R.string.determine));
        closeMinePoolDialog.setButtonNoStatus(false);
        closeMinePoolDialog.setBtYesBackGroundJinSe(R.drawable.shape_shop_btn);
        closeMinePoolDialog.setDialogOnClickListener(new DialogOnClickListener() {
            @Override
            public void Determine(String content) {
                closeMinePoolDialog.dismiss();
            }

            @Override
            public void Cancel() {
                closeMinePoolDialog.dismiss();
            }
        });
        closeMinePoolDialog.show();
    }

    public void showLoading() {
        if (progressbar != null) {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading() {
        if (progressbar != null) {
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * 积分账户查询接口
     */
    private void pointsAccount() {
        ServerApi.getInstance().pointsAccount(cnt)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        if (CommonParametersUtils.validationResultsForPhp(cnt, response)) {
                            try {
                                hideLoading();
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data != null) {
                                    String pendingDbmValue = data.optString("pending_dbm_value");
                                    String returnedDbmNum = data.optString("returned_dbm_num");
                                    String returnedPoints = data.optString("returned_points");
                                    String linkEarn = data.optString("link_earn");
                                    unspentPoints = data.optString("unspent_points");
                                    ruleInfo = data.optString("rule_info");

                                    tvWaitingForDbm.setText(cnt.getResources().getString(R.string.to_return_the_dbm)+ cnt.getResources().getString(R.string.ren_min_bi) + pendingDbmValue);
                                    tvCashBackDbm.setText(cnt.getResources().getString(R.string.dbn_has_been_returned) + returnedDbmNum);
                                    tvCashBackIntegral.setText(cnt.getResources().getString(R.string.cash_back_points) + returnedPoints);
                                    tvPointBalance.setText(cnt.getResources().getString(R.string.point_balance) + unspentPoints);
                                    tvTodayLinkBack.setText(cnt.getResources().getString(R.string.today_is_link_back) + linkEarn);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        XToastUtils.showRoundRectToast(e.getMessage());
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }

    /**
     * 积分账户操作记录接口
     */
    private void pointsAccountLogs() {
        ServerApi.getInstance().pointsAccountLogs(cnt, curragePage + "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        if (CommonParametersUtils.validationResultsForPhp(cnt, response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.optJSONArray("data");
                                if (curragePage == 1) {
                                    cashBackList.clear();
                                }
                                if (data != null) {
                                    Gson gson = new Gson();
                                    for (int i = 0; i < data.length(); i++) {
                                        MyCashBackModel model = gson.fromJson(data.get(i).toString(), MyCashBackModel.class);
                                        cashBackList.add(model);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                if (cashBackList.size() > 0) {
                                    llNoData.setVisibility(View.GONE);
                                } else {
                                    llNoData.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        XToastUtils.showRoundRectToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            curragePage = 1;
            pointsAccount();
            pointsAccountLogs();
        }
    }


    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColorTransformEnable(false)
                .keyboardEnable(false)
                .navigationBarColor(R.color.navigation_bar_color)
                .init();
    }


    @OnClick({R.id.rl_point_balance,R.id.rl_today_link_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_point_balance://积分余额
                break;
            case R.id.rl_today_link_back://今日链接返现
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dbmall_my_cash;
    }

    @Override
    public void onLoadMore(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore();
        curragePage++;
        pointsAccountLogs();
    }

    @Override
    public void onRefresh(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh();
        curragePage = 1;
        pointsAccount();
        pointsAccountLogs();
    }
}
