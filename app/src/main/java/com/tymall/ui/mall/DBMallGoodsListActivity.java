package com.tymall.ui.mall;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tymall.R;
import com.tymall.ui.adapter.GoodsListAdapter;
import com.tymall.app.Constant;
import com.tymall.app.MyEventBusModel;
import com.tymall.base.XBaseActivity;
import com.tymall.model.GoodsListModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.ServerApi;
import com.tymall.utils.ClickOnUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.LogUtil;
import com.tymall.utils.SP;
import com.tymall.utils.XToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * <pre>
 *     @author wds
 *     desc  : DBMall 商品分类跳转到原生
 *     revise: 原生webView
 * </pre>
 */

public class DBMallGoodsListActivity extends XBaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String categoryId, title;
    private GoodsListAdapter mAdapter = null;

    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private List<GoodsListModel.GoodsListBean> datalist = new ArrayList<>();
    private View emptyView;
    private boolean isRefresh = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbmall_good_iist;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        String Url = getIntent().getStringExtra(Constant.GOODS_ID);
        if (TextUtils.isEmpty(Url)) {
            return;
        }
        try {
            if (Url.indexOf("categroyId=") != -1) {//判断是否存在截取的字符
                categoryId = Url.substring(Url.indexOf("=") + 1, Url.indexOf("&"));
            }
        } catch (Exception e) {

        }
        title = getIntent().getStringExtra(Constant.MALL_TITLE);
        titleBar.setTitleSubText(TextUtils.isEmpty(title) ? "" : title);
        LogUtil.d("getDBMallGoodsListById", "start");
        titleBar.setStatusBarLightMode(false)
                .setTitleMainTextMarquee(true);
        setupRecyclerView();
        refresh(false);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(SP.get(Constant.SKIP_MODE_NEED_FINISH, false)){
            SP.set(Constant.SKIP_MODE_NEED_FINISH,false);
            finish();
        }

    }


    public void showLoading() {
        if (progressbar != null) {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    public void finishLoadView() {
        if (progressbar != null) {
            progressbar.setVisibility(View.GONE);
        }
    }

    public void setupRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter = new GoodsListAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                GoodsListModel.GoodsListBean goodsListBean = datalist.get(position);
                if(goodsListBean == null || TextUtils.isEmpty(goodsListBean.getGoodDetailsUrl())){
                    return;
                }
                if (ClickOnUtils.isFastClick())
                    DBMallH5Activity.openH5(DBMallGoodsListActivity.this,goodsListBean.getGoodDetailsUrl(),TextUtils.isEmpty(goodsListBean.getName())?"":goodsListBean.getName());
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(true);
            }
        });




    }

    private void refreshSuccessView(boolean isloadMore) {
        setData(!isloadMore);
        if (!isloadMore) {
            mAdapter.setEnableLoadMore(true);
            finishLoading();
        }
        isRefresh = false;

    }

    private void refreshFailView(boolean isloadMore) {
        if (isloadMore) {
            mAdapter.loadMoreFail();
        } else {
            mAdapter.setEnableLoadMore(true);
            finishLoading();
        }
        isRefresh = false;

    }

    private void loadMore() {
        mRecyclerView.setVisibility(View.VISIBLE);
        refreshData(true);
    }

    private void setData(boolean isRefresh) {
        mNextRequestPage++;
        final int size = datalist == null ? 0 : datalist.size();
        if (isRefresh) {
            if (size > 0) {
                mAdapter.setNewData(datalist);
            } else {
                mAdapter.setEmptyView(emptyView);
            }
        } else {
            if (size > 0) {
                mAdapter.addData(datalist);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    private void finishLoading() {
        // 延时1s关闭下拉刷新
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        finishLoadView();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 800);
        }
    }

    @Subscribe
    public void onEvent(MyEventBusModel myEventBusModel) {

    }

    public void refresh(boolean isPullRefresh) {
        if (isRefresh) {
            return;
        }
        if (isPullRefresh) {
            mRecyclerView.setVisibility(View.VISIBLE);
            finishLoadView();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            showLoading();
        }
        mRecyclerView.scrollToPosition(0);
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        isRefresh = true;

        refreshData(false);
    }

    private void refreshData(boolean isloadMore) {
        if (isloadMore) {
            finishLoadView();
        }


        ServerApi.getInstance().getDBMallGoodsListById(getContext(), mNextRequestPage + "", categoryId)

                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<GoodsListModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<GoodsListModel> response) {
                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                GoodsListModel goodsListModel = response.getData();
                                if (goodsListModel == null) {
                                    refreshFailView(isloadMore);
                                    return;
                                }
                                datalist = goodsListModel.getGoodsList();

                                refreshSuccessView(isloadMore);
                            } catch (Exception e) {

                            }


                        }else {
                            refreshFailView(isloadMore);

                        }


                    }


                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        XToastUtils.showRoundRectToast(e.getMessage());
                        refreshFailView(isloadMore);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}




