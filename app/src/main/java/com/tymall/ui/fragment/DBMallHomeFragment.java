package com.tymall.ui.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding3.view.RxView;
import com.tymall.R;
import com.tymall.ui.adapter.DBMallHomeAdapter;
import com.tymall.ui.adapter.DBMallMainGoodsListAdapter;
import com.tymall.ui.adapter.DBMallViewHolder;
import com.tymall.app.Constant;
import com.tymall.app.MyEventBusModel;
import com.tymall.base.XBaseFragment;
import com.tymall.model.DBMallBannerModel;
import com.tymall.model.DBMallPageModel;
import com.tymall.model.GoodsListNewModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.mall.DBMallGoodsListActivity;
import com.tymall.ui.mall.DBMallH5Activity;
import com.tymall.ui.mall.DBMallSearchActivity;
import com.tymall.utils.ClickOnUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.GlideImageLoader;
import com.tymall.utils.LogUtil;
import com.tymall.utils.SP;
import com.tymall.utils.SizeUtil;
import com.tymall.utils.StatusBarUtils;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.pulltorefresh.PtrFrameLayout;
import com.tymall.view.pulltorefresh.PtrHandler;
import com.tymall.view.pulltorefresh.header.JDHeaderView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DBMallHomeFragment extends XBaseFragment implements JDHeaderView.RefreshDistanceListener {
    private static int DISTANCE_WHEN_TO_SELECTED = 40;


    @BindView(R.id.scanning_layout)
    LinearLayout scanninglayout;

    @BindView(R.id.home_title_bar_layout)
    View homeTitleBarLayout;
    @BindView(R.id.home_title_bar_bg_view)
    View homeTitleBarBgView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.et_search)
    EditText etSearch;


    @BindView(R.id.rootView)
    FrameLayout rootView;


    @BindView(R.id.rotate_header_list_view_frame)
    JDHeaderView mPtrFrame;
    Unbinder unbinder;
    private int headHeight;
    private float scrollDistance;

    List<GoodsListNewModel.RecordsBean> goodsList = new ArrayList<>();
    List<DBMallBannerModel> bannerList = new ArrayList<>();
    List<DBMallPageModel> pageList = new ArrayList<>();
    List<String> imagesUrls = new ArrayList<>();

    private DBMallHomeAdapter adapter_footer, adapter_page, adapter_banner = null;
    public DBMallMainGoodsListAdapter adapter_goods = null;

    private int pageNum = 5;
    private int pageCulumn = 5;
    private boolean hasMore = true;
    private int page = 1;

    private boolean isLoading = false;
    private boolean isSkipH5GoodsList = true;
    private int distanceY;
    private SkeletonScreen skeletonScreen;
    private boolean isShow = false;

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<DBMallHomeFragment> activityWeakReference;

        MyHandler(DBMallHomeFragment activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().skeletonScreen.hide();
            }
        }
    }


    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.icon_shop_default)//图片加载出来前，显示的图片
            .fallback(R.mipmap.icon_shop_default) //url为空的时候,显示的图片
            .error(R.mipmap.icon_shop_default);//图片加载失败后，显示的图片


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    class ViewType {
        public static final int BANNER = 1;
        public static final int DIVIDE = 2;
        public static final int MENU = 3;
        public static final int PAGE_BOTTOM = 4;
        public static final int GOODSLIST = 5;
        public static final int LOADMORE = 6;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dbmall_home;
    }


    @Override
    protected void initView() {
        StatusBarUtils.setFullScreenWithStatusBar(getActivity());
        ViewGroup.LayoutParams layoutParams = homeTitleBarBgView.getLayoutParams();
        layoutParams.height = StatusBarUtils.getStatusBarHeight(getContext());
        homeTitleBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headHeight = homeTitleBarLayout.getHeight();
                homeTitleBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        addDisposable(RxView.clicks(scanninglayout)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    getActivity().finish();
                }));


        etSearch.setFocusable(false);
        addDisposable(RxView.clicks(etSearch)
                .throttleFirst(0, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    startActivity(new Intent(getActivity(), DBMallSearchActivity.class));
                }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                distanceY += dy;
                if (distanceY > SizeUtil.dip2px(mActivity, 20)) {
                    homeTitleBarBgView.setBackgroundColor(getResources().getColor(R.color.setting_sign_out_color));
                    homeTitleBarLayout.setBackgroundColor(getResources().getColor(R.color.setting_sign_out_color));
                    homeTitleBarBgView.setAlpha(distanceY * 1.0f / SizeUtil.dip2px(mActivity, 100));
                } else {
                    homeTitleBarBgView.setBackgroundColor(0);
                    homeTitleBarLayout.setBackgroundColor(0);
                }

                if (hasMore) {
                    VirtualLayoutManager lm = (VirtualLayoutManager) recyclerView.getLayoutManager();
                    int last = 0, total = 0;
                    last = lm.findLastVisibleItemPosition();
                    total = recyclerView.getAdapter().getItemCount();
                    if (last > 0 && last >= total - 1) {
                        if (!isLoading) {
                            hasMore = false;
                            LogUtil.d("goodlist_Text", "loadmore start" + "page = " + page);
                            page++;
                            refreshGoodsData(true);
                        }
                    }
                }


            }
        });




        initPtrFrame();
        initVLayout();


        if(TextUtils.isEmpty(SP.get(Constant.GOODS_LIST_CACHE, ""))) {
            try {
                skeletonScreen = Skeleton.bind(rootView)
                        .load(R.layout.activity_view_skeleton_dbmall)
                        .duration(1000)
                        .color(R.color.shimmer_color)
                        .angle(0)
                        .show();
                isShow = true;
            }catch (Exception e){

            }
        }


        refreshData();



        String  phoneUrl = getActivity().getIntent().getStringExtra("isPhoneUrl");
        if(!TextUtils.isEmpty(phoneUrl)){
            DBMallH5Activity.openH5NoToken(getActivity(),phoneUrl);
            return;
        }

    }

    public void refreshGoodsData(boolean isLoadMoae) {
        if(!isLoadMoae){
            try {
                String cacheList = SP.get(Constant.GOODS_LIST_CACHE, "");
                if (!TextUtils.isEmpty(cacheList)) {
                    List<GoodsListNewModel.RecordsBean> datalist = new Gson().fromJson(cacheList, new TypeToken<List<GoodsListNewModel.RecordsBean>>() {
                    }.getType());
                    if (datalist != null && datalist.size() > 0) {

                        if (page == 1 && goodsList != null) {
                            goodsList.clear();
                        }

                        goodsList.addAll(datalist);
                        adapter_goods.notifyDataSetChanged();

                    }

                }
            }catch (Exception e){

            }
        }

        isLoading = true;
        ServerApi.getInstance().categoryGoodlist(getContext(), page + "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<GoodsListNewModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<GoodsListNewModel> response) {
                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                GoodsListNewModel goodsListNewModel = response.getData();
                                if (goodsListNewModel == null) {
                                    return;
                                }
                                LogUtil.d("goodlist_Text", "jiazai done" + "page = " + page);

                                List<GoodsListNewModel.RecordsBean> datalist = goodsListNewModel.getRecords();



                                if (page == 1 && goodsList != null) {
                                    SP.set(Constant.GOODS_LIST_CACHE,new Gson().toJson(datalist));
                                    goodsList.clear();
                                }

                                goodsList.addAll(datalist);

                                if(isLoadMoae) {

                                    if (datalist.size() <= 0) {
                                        hasMore = false;
                                    } else {
                                        hasMore = true;
                                    }
                                }
                                adapter_goods.notifyDataSetChanged();

                                if(isShow){
                                    isShow = false;
                                    MyHandler myHandler = new MyHandler(DBMallHomeFragment.this);
                                    myHandler.sendEmptyMessageDelayed(1, 1);
                                }


                            } catch (Exception e) {

                            }


                        }

                        isLoading = false;
                        finishLoading();

                    }


                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        isLoading = false;
                        XToastUtils.showRoundRectToast(e.getMessage());
                        finishLoading();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void onPositionChange(int currentPosY) {
        if (currentPosY > 0) {
            if (homeTitleBarLayout.getVisibility() == View.VISIBLE) {
                homeTitleBarLayout.setVisibility(View.GONE);
            }
        } else {
            if (homeTitleBarLayout.getVisibility() == View.GONE) {
                homeTitleBarLayout.setVisibility(View.VISIBLE);
                distanceY = 0;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    private void refreshData() {
        LogUtil.d("wds","refreshData");
        page = 1;
        hasMore = true;
        refreshBannerData();
        refreshPageData();
        refreshGoodsData(false);
    }


    @Subscribe
    public void onEvent(MyEventBusModel myEventBusModel) {

    }


    private void initVLayout() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        recyclerView.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        adapter_banner = new DBMallHomeAdapter(getActivity(), new LinearLayoutHelper(), 1, ViewType.BANNER) {

            @Override
            public DBMallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DBMallViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, parent, false));
            }

            @Override
            public void onBindViewHolder(DBMallViewHolder holder, int position) {
                Banner banner = (Banner) holder.itemView;
                if (banner != null) {
                    //设置banner样式
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages(imagesUrls);
                    //设置banner动画效果
                    banner.setBannerAnimation(Transformer.Default);
                    //设置自动轮播，默认为true
                    banner.isAutoPlay(true);
                    //设置轮播时间
                    banner.setDelayTime(3000);
                    //设置指示器位置（当banner模式中有指示器时）
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            DBMallBannerModel bannersBean = bannerList.get(position);
                            if (bannersBean != null && !TextUtils.isEmpty(bannersBean.getUrl())) {
                                if (ClickOnUtils.isFastClick())
                                    DBMallH5Activity.openH5(getActivity(), bannersBean.getUrl(), TextUtils.isEmpty(bannersBean.getName()) ? "" : bannersBean.getName());


                            }


                        }
                    });
                    banner.start();
                }
            }
        };
        adapters.add(adapter_banner);

        LinearLayoutHelper dividerHelper = new LinearLayoutHelper(1);
        dividerHelper.setBgColor(getResources().getColor(R.color.eos_gray_bg));
        adapters.add(new DBMallHomeAdapter(getActivity(), dividerHelper, 1, ViewType.DIVIDE) {

            @Override
            public DBMallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DBMallViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_divider, parent, false));
            }

            @Override
            public void onBindViewHolder(DBMallViewHolder holder, int position) {

            }
        });

        GridLayoutHelper mGridlayoutHelper;
        mGridlayoutHelper = new GridLayoutHelper(pageCulumn);
        mGridlayoutHelper.setAspectRatio(5f);
        mGridlayoutHelper.setVGap(9);// 控制子元素之间的垂直间距
        mGridlayoutHelper.setHGap(0);// 控制子元素之间的水平间距
        mGridlayoutHelper.setMargin(0, 11, 0, 0);
        adapter_page = new DBMallHomeAdapter(getActivity(), mGridlayoutHelper, pageNum, ViewType.MENU) {
            @Override
            public DBMallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DBMallViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_dbmall_page, parent, false));
            }

            @Override
            public void onBindViewHolder(DBMallViewHolder holder, int position) {


                if (pageList == null || pageList.size() <= 0) {
                    return;
                }


                try {
                    DBMallPageModel data = pageList.get(position);
                    if (data == null) {
                        return;
                    }

                    LinearLayout linearLayout = (LinearLayout) holder.itemView;
                    ImageView pageImg = (ImageView) linearLayout.findViewById(R.id.page_img);
                    TextView pageText = (TextView) linearLayout.findViewById(R.id.page_text);
                    pageText.setText(TextUtils.isEmpty(data.getTitle()) ? "" : data.getTitle());
                    if (data.getIcon() != null) {
                        Glide.with(holder.itemView.getContext()).load(data.getIcon()).apply(options).into(pageImg);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DBMallPageModel  recommendBean = pageList.get(position);
                            if (recommendBean == null) {
                                return;
                            }
                            if (recommendBean != null ) {
                                    Intent intent = new Intent(getActivity(), DBMallGoodsListActivity.class);
                                    intent.putExtra(Constant.MALL_TITLE, recommendBean.getTitle());
                                    intent.putExtra(Constant.GOODS_ID, recommendBean.getId());
                                    startActivity(intent);
                            }

                        }
                    });
                } catch (Exception e) {

                }
            }
        };
        adapters.add(adapter_page);

        LinearLayoutHelper bottomlinearHelper = new LinearLayoutHelper(1);
        bottomlinearHelper.setMargin(0, 12, 0, 26);
        adapters.add(new DBMallHomeAdapter(getActivity(), bottomlinearHelper, 1, ViewType.PAGE_BOTTOM) {

            @Override
            public DBMallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DBMallViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_page_bottom, parent, false));
            }

            @Override
            public void onBindViewHolder(DBMallViewHolder holder, int position) {

            }
        });

        //Goods List 布局
        GridLayoutHelper mGoodlayoutHelper;
        mGoodlayoutHelper = new GridLayoutHelper(2);
//        mGoodlayoutHelper.setAspectRatio(1f);
//        mGoodlayoutHelper.setVGap(0);// 控制子元素之间的垂直间距
//        mGoodlayoutHelper.setHGap(0);// 控制子元素之间的水平间距
//        mGoodlayoutHelper.setMargin(0, 0, 0, 0);
        adapter_goods = new DBMallMainGoodsListAdapter(getActivity(), goodsList, mGoodlayoutHelper);
        adapters.add(adapter_goods);


        SingleLayoutHelper loadMorelinearHelper = new SingleLayoutHelper();
        loadMorelinearHelper.setBgColor(getResources().getColor(R.color.eos_gray_bg));
        adapter_footer = new DBMallHomeAdapter(getActivity(), loadMorelinearHelper, 1, ViewType.LOADMORE) {

            @Override
            public DBMallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DBMallViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_footer, parent, false));
            }

            @Override
            public void onBindViewHolder(DBMallViewHolder holder, int position) {
                LinearLayout linearLayout = holder.itemView.findViewById(R.id.food);
                TextView textView = holder.itemView.findViewById(R.id.textview);
                ProgressBar progressBar = holder.itemView.findViewById(R.id.progressbar);
                if (hasMore) {
                    textView.setText(R.string.loading_msg);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    textView.setText(getString(R.string.string_no_more_data));
                    progressBar.setVisibility(View.GONE);
                }
                if (goodsList != null) {
                    if (goodsList.size() <= 0) {
                        linearLayout.setVisibility(View.GONE);
                        loadMorelinearHelper.setBgColor(getResources().getColor(R.color.white));

                    } else {
                        loadMorelinearHelper.setBgColor(getResources().getColor(R.color.eos_gray_bg));
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        };

        adapters.add(adapter_footer);
        delegateAdapter.addAdapters(adapters);

    }


    private void finishLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (mPtrFrame != null) {
                    mPtrFrame.refreshComplete();
                }


            }
        }, 1000);


    }

    /**
     * 初始化下拉刷新
     */
    private void initPtrFrame() {
        mPtrFrame.setOnRefreshDistanceListener(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isShow = false;
                LogUtil.d("wds","onRefreshBegin");

                refreshData();
            }
        });


        // 是否进入页面就开始显示刷新动作
        /*mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);*/
    }


    public void refreshBannerData() {


        ServerApi.getInstance().getDBMallHomeBanner(getContext())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<List<DBMallBannerModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<List<DBMallBannerModel>> response) {
                        if (CommonParametersUtils.checkToken(getContext(), response)) {

                            try {
                                bannerList = response.getData();

                                if (bannerList == null || bannerList.size() <= 0) {
                                    return;
                                }
                                if (imagesUrls != null) {
                                    imagesUrls.clear();
                                }
                                for (int i = 0; i < bannerList.size(); i++) {
                                    imagesUrls.add(bannerList.get(i).getPic());
                                }

                                adapter_banner.notifyDataSetChanged();


                            } catch (Exception e) {

                            }
                            LogUtil.d("dbmall_loadmore", "start");


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


    public void refreshPageData() {
  LogUtil.d("wds","refreshPageData");

        ServerApi.getInstance().getGoodsMode(getContext())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<List<DBMallPageModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<List<DBMallPageModel>> response) {
                        if (CommonParametersUtils.checkToken(getContext(), response)) {

                            try {
                                pageList = response.getData();
                                if (pageList != null && pageList.size() > 0) {
                                    pageNum = pageList.size();
                                    if (pageNum > 10) {
                                        pageNum = 10;
                                    }


                                }
                                adapter_page.notifyDataSetChanged();


                            } catch (Exception e) {

                            }
                            LogUtil.d("dbmall_loadmore", "start");


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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden) {
            try {
                if (isShow) {
                    isShow = false;
                    MyHandler myHandler = new MyHandler(DBMallHomeFragment.this);
                    myHandler.sendEmptyMessageDelayed(1, 1);
                }
            } catch (Exception e){

            }

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


}
