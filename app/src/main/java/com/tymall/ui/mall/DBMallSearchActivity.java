package com.tymall.ui.mall;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.lzy.okgo.OkGo;
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
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.LogUtil;
import com.tymall.utils.SP;
import com.tymall.utils.StatusBarUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * <pre>
 *     @author wds
 *     desc  : DBMall
 *     revise: 原生webView
 * </pre>
 */

public class DBMallSearchActivity extends XBaseActivity {


    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.cancel)
    TextView cancel;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    View head;
    @BindView(R.id.status_mask)
    View statusMask;

    @BindView(R.id.search_no_coin)
    TextView searchNoCoin;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private String keyword;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GoodsListAdapter mAdapter = null;

    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private List<GoodsListModel.GoodsListBean> datalist = new ArrayList<>();

    private View emptyView;
    private boolean isRefresh = false;
    private InputMethodManager inputMethodManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbmall_search;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        ViewGroup.LayoutParams layoutParams = statusMask.getLayoutParams();
        layoutParams.height = StatusBarUtils.getStatusBarHeight(getContext());
        StatusBarUtils.setStatusBarDarkMode(this);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
        //显示软键盘
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        addDisposable(RxView.clicks(cancel)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    finish();
                }));

        setupRecyclerView();

        RxTextView.textChangeEvents(etSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                       String  key = etSearch.getText().toString().trim();
                       if(!TextUtils.isEmpty(keyword) && keyword.equals(key)){
                           return;
                       }
                       keyword = key;
                        searchNoCoin.setVisibility(View.GONE);
                        OkGo.getInstance().cancelTag("querySearchResult");
                        if (TextUtils.isEmpty(keyword)) {
                            LogUtil.d("search_db","empty");
                            mSwipeRefreshLayout.setVisibility(View.GONE);
                            finishLoading();
                            finishLoadView();
                        } else {
                            LogUtil.d("search_db","start");

                            refresh(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });




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

        ServerApi.getInstance().querySearchResult(getContext(), mNextRequestPage + "", keyword)
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
                                    mSwipeRefreshLayout.setVisibility(View.GONE);

                                    LogUtil.d("search_db","datalist empty" );

                                    return;
                                }

                                datalist = goodsListModel.getGoodsList();
                                LogUtil.d("search_db","datalist .size=" +datalist.size());
                                refreshSuccessView(isloadMore);
                                if(datalist != null ){
                                    if(datalist.size() <= 0) {
                                        searchNoCoin.setVisibility(View.VISIBLE);
                                        mSwipeRefreshLayout.setVisibility(View.GONE);

                                    }else {
                                        searchNoCoin.setVisibility(View.GONE);
                                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                                    }
                                }



                            } catch (Exception e) {
                                refreshFailView(isloadMore);
                                mSwipeRefreshLayout.setVisibility(View.GONE);


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
                        searchNoCoin.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);

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




