package com.tymall.ui.fragment.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tymall.R;
import com.tymall.app.UrlConfig;
import com.tymall.base.XBaseFragment;
import com.tymall.model.OrderListModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.adapter.OrderAdapter;
import com.tymall.ui.mall.DBMallH5Activity;
import com.tymall.utils.ClickOnUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.ToastUtils;

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
 * 已完成
 */
public class CompletedFragment extends XBaseFragment implements OnRefreshListener, OnLoadMoreListener {

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

    @Override
    protected void initView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        orderList = new ArrayList<OrderListModel.OrderListBean>();
        rlRecycleview.setHasFixedSize(true);
        rlRecycleview.setLayoutManager(new LinearLayoutManager(cnt));
        rlRecycleview.setNestedScrollingEnabled(false);
        adapter = new OrderAdapter(cnt,orderList);
        rlRecycleview.setAdapter(adapter);
        adapter.set_OnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClickOne(View view, int position) {

            }

            @Override
            public void onItemClickTwo(View view, int position) {

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
     * 查询订单列表
     * 0:未付款 101:订单已取消 201:待发货 202: 待收货 301:订单已完成
     * 全部""
     */
    private void getOrderList() {
        ServerApi.getInstance().getOrderList(getContext(), pageCount, "301")
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
     * 刷新订单列表
     */
    public void setRefreshOrderList() {
        pageCount = 1;
        getOrderList();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_completed;
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
}
