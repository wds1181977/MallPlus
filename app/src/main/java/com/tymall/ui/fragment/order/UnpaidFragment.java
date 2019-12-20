package com.tymall.ui.fragment.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tymall.R;
import com.tymall.ui.adapter.OrderAdapter;
import com.tymall.app.Constant;
import com.tymall.app.UrlConfig;
import com.tymall.base.XBaseFragment;
import com.tymall.model.OrderListModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.fragment.PayFragment;
import com.tymall.ui.mall.DBMallH5Activity;
import com.tymall.ui.mall.DBMallManager;
import com.tymall.utils.ClickOnUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.DialogController;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.DBankDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 未付款
 */
public class UnpaidFragment extends XBaseFragment implements OnRefreshListener, OnLoadMoreListener, PayFragment.PayObjListener {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    /**
     * 无数据时候显示
     */
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.rl_recycleview)
    RecyclerView rlRecycleview;

    private OrderAdapter adapter;
    private List<OrderListModel.OrderListBean> orderList;
    private int pageCount;

    private PayFragment mPayFrgment;
    private Bundle mBundle;

    @Override
    protected void initView() {
        if (mPayFrgment == null) {
            mPayFrgment = PayFragment.getInstance(mBundle = new Bundle());

        }
        mPayFrgment.setPayObjListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        orderList = new ArrayList<OrderListModel.OrderListBean>();
        rlRecycleview.setHasFixedSize(true);
        rlRecycleview.setLayoutManager(new LinearLayoutManager(cnt));
        rlRecycleview.setNestedScrollingEnabled(false);
        adapter = new OrderAdapter(cnt, orderList);
        rlRecycleview.setAdapter(adapter);
        //0:未付款 101:订单已取消 201:待发货 202: 待收货 301:订单已完成
        adapter.set_OnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClickOne(View view, int position) {
                OrderListModel.OrderListBean orderListBean = orderList.get(position);
                if (orderListBean.getOrderStatus() == 0) {
                    //未付款---取消订单
                    showPromptDialog(orderListBean.getId());
                }
            }

            @Override
            public void onItemClickTwo(View view, int position) {
                OrderListModel.OrderListBean orderListBean = orderList.get(position);
                if (orderListBean.getOrderStatus() == 0) {
                    if (orderListBean.getCanBePaid() == 0) {
                        XToastUtils.showRoundRectToast(getString(R.string.string_damall_no_pay));
                        return;
                    }
                    DBMallManager.DBMallPayParams payParams = new DBMallManager.DBMallPayParams()
                            .actualPrice(TextUtils.isEmpty(orderListBean.getActualPrice()) ? "" : orderListBean.getActualPrice())
                            .symbol(TextUtils.isEmpty(orderListBean.getCurrencyName()) ? "" : orderListBean.getCurrencyName())
                            .pId(TextUtils.isEmpty(orderListBean.getPayId()) ? "" : orderListBean.getPayId())
                            .type(TextUtils.isEmpty(orderListBean.getCurrencyType()) ? "" : orderListBean.getCurrencyType())
                            .currencyId("11")
                            .amount(TextUtils.isEmpty(orderListBean.getPriceCurrency()) ? "" : orderListBean.getPriceCurrency())
                            .orderId(TextUtils.isEmpty(orderListBean.getId()) ? "" : orderListBean.getId());
                    showPayDialog(payParams);
                }
            }

            @Override
            public void onItemOnClickListener(View view, int position) {
                if (ClickOnUtils.isFastClickFiveHundred()) {
                    String orderId = orderList.get(position).getId();
                    String str1 = "orders/orders-detail.html?orderId=";
                    String str2 = "&t=";
                    String orderDetailsUrl = UrlConfig.getMallDomainBaseUrl() + str1 + orderId + str2;
                    DBMallH5Activity.openH5(getActivity(), orderDetailsUrl, "");
                }
            }
        });
        pageCount = 1;
        getOrderList();
    }


    /**
     * 取消订单
     */
    private void cancelOrder(String orderId) {
        ServerApi.getInstance().cancelOrder(getContext(), orderId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<String> response) {
                        hideLoading();
                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                pageCount = 1;
                                getOrderList();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        ToastUtils.show(e.getMessage());
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 查询订单列表
     * 0:未付款 101:订单已取消 201:待发货 202: 待收货 301:订单已完成
     * 全部""
     */
    private void getOrderList() {
        ServerApi.getInstance().getOrderList(getContext(), pageCount, "0")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        if (CommonParametersUtils.checkTokenNew(getContext(), response)) {
                            try {
                                if (pageCount == 1) {
                                    orderList.clear();
                                }
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.optJSONObject("data");
                                Gson gson = new Gson();
                                OrderListModel orderListModel = gson.fromJson(data.toString(), OrderListModel.class);
                                if (orderListModel == null) {
                                    return;
                                }
                                List<OrderListModel.OrderListBean> listBeans = orderListModel.getOrderList();
                                orderList.addAll(listBeans);
                                adapter.notifyDataSetChanged();

                                if (getActivity() != null) {
                                    if (orderList.size() == 0) {
                                        tvNoData.setVisibility(View.VISIBLE);
                                    } else {
                                        tvNoData.setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

    /**
     * 取消订单  提示弹窗
     *
     * @param orderId
     */
    private void showPromptDialog(String orderId) {
        String contentString = cnt.getResources().getString(R.string.are_you_sure_you_want_to_cancel_the_order);
        DialogController.getInstance().showDBankDialog(cnt, ""
                , contentString
                , cnt.getResources().getString(R.string.determine), cnt.getResources().getString(R.string.cancle)
                , R.drawable.shape_shop_btn
                , new DBankDialog.OnSubmitClickListener() {
                    @Override
                    public void onSubmitClick() {
                        cancelOrder(orderId);
                    }
                });
    }


    private void showPayDialog(DBMallManager.DBMallPayParams payParams) {
        if (payParams == null) {
            ToastUtils.show(getString(R.string.string_eos_tx_error));
            return;
        }
        mBundle.putInt(Constant.EOS_PAY_STATE, 203);
        mBundle.putSerializable(Constant.MALL_PAY_PARAMS, payParams);
        mPayFrgment.show(getActivity().getSupportFragmentManager(), "mPayFrgment");
    }


    @Override
    public void paySuccessObj(DBMallManager.DBMallPayParams payParams) {
        DBMallManager.getInstance().makePayMarket(getActivity(), payParams);
    }


    /**
     * 刷新订单列表
     */
    public void setRefreshOrderList() {
        pageCount = 1;
        getOrderList();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageCount = 1;
        getOrderList();
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore();
        pageCount++;
        getOrderList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh();
        pageCount = 1;
        getOrderList();
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
    protected int getLayoutId() {
        return R.layout.fragment_unpaid;
    }
}
