package com.tymall.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aries.ui.view.title.TitleBarView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding3.view.RxView;
import com.tymall.R;
import com.tymall.ui.adapter.ShopAdapter;
import com.tymall.app.UrlConfig;
import com.tymall.base.XBaseFragment;
import com.tymall.model.ShopModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.mall.DBMallH5Activity;
import com.tymall.utils.CacheUtils;
import com.tymall.utils.ClickOnUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.DialogController;
import com.tymall.utils.GetToast;
import com.tymall.utils.StringUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.DBankDialog;
import com.tymall.view.OnItemAddOrDeleteClickListener;
import com.tymall.view.OnItemMoneyClickListener;
import com.tymall.view.OnViewItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ShopFragment extends XBaseFragment {

    @BindView(R.id.rl_recycleview)
    RecyclerView rlRecycleview;

    @BindView(R.id.titleBar)
    TitleBarView titleBar;

    /**
     * 全选
     */
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;


    /**
     * 合计价格
     */
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;

    /**
     * 结算/删除按钮
     */
    @BindView(R.id.tv_determine)
    TextView tvDetermine;


    /**
     * 购物车无数据
     */
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    /**
     * 底部布局
     */
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    private boolean isEditing;//是否处于编辑状态
    private int totalCheckedCount;//勾选的商品总数量，店铺条目不计算在内
    private String totalPrice = "0";//勾选的商品总价格

    private ShopAdapter shopAdapter;
    private List<ShopModel.CartListBean> shopList;

    private boolean isAddSuccess = true;

    private boolean isLessSucess = true;


    @Override
    protected void initView() {
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
                isEditing = !isEditing;
                titleBar.setRightText(isEditing ? cnt.getResources().getString(R.string.botton_complete) : cnt.getResources().getString(R.string.management));
                setBottomText();
            }
        });
        titleBar.setRightVisible(false);
        shopList = new ArrayList<ShopModel.CartListBean>();
        rlRecycleview.setLayoutManager(new LinearLayoutManager(cnt));
        shopAdapter = new ShopAdapter(cnt, shopList);
        rlRecycleview.setAdapter(shopAdapter);

        addDisposable(RxView.clicks(tvDetermine)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    BillingOrDeleting();
                }));

        setAdapterOnclicklistener();
        queryGetCartIndex();


    }


    @Override
    public void onResume() {
        super.onResume();
        if ("301".equals(CacheUtils.getIsRefreshShoppingCart())) {
            CacheUtils.setIsRefreshShoppingCart("-1");
            queryGetCartIndex();
        }
    }

    public void showLoadingView() {
        if (progressbar != null) {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoadingView() {
        if (progressbar != null) {
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * 查询购物车数据
     */
    private void queryGetCartIndex() {
        ServerApi.getInstance().queryGetCartIndex(getContext())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadingView();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<ShopModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<ShopModel> response) {
                        hideLoadingView();
                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                shopList.clear();
                                ShopModel shopModel = response.getData();
                                if (shopModel == null) {
                                    return;
                                }
                                List<ShopModel.CartListBean> cartList = shopModel.getCartList();
                                shopList.addAll(cartList);
                                shopAdapter.notifyDataSetChanged();
                                checkboxAll.setChecked(false);
                                if (shopList.size() > 0) {
                                    titleBar.setRightVisible(true);
                                    llNoData.setVisibility(View.GONE);
                                    rlBottom.setVisibility(View.VISIBLE);
                                } else {
                                    titleBar.setRightVisible(false);
                                    llNoData.setVisibility(View.VISIBLE);
                                    rlBottom.setVisibility(View.GONE);
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
                        hideLoadingView();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 删除购物车数据
     */
    private void deleteCart(String ids) {
        ServerApi.getInstance().deleteCart(getContext(), ids)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<ShopModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<ShopModel> response) {
                        hideLoading();
                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                shopList.clear();
                                ShopModel shopModel = response.getData();
                                if (shopModel == null) {
                                    return;
                                }
                                List<ShopModel.CartListBean> cartList = shopModel.getCartList();
                                shopList.addAll(cartList);
                                shopAdapter.notifyDataSetChanged();

                                checkboxAll.setChecked(false);
                                showCommodityCalculation();

                                if (shopList.size() > 0) {
                                    titleBar.setRightVisible(true);
                                    llNoData.setVisibility(View.GONE);
                                    rlBottom.setVisibility(View.VISIBLE);
                                } else {
                                    titleBar.setRightVisible(false);
                                    llNoData.setVisibility(View.VISIBLE);
                                    rlBottom.setVisibility(View.GONE);
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
     * 添加购物车中商品的数量
     *
     * @param goodsId
     */
    private void addCartNum(String goodsId, int onePosition, int position) {
        ServerApi.getInstance().addCart(getContext(), goodsId, "1")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new BaseSubscriber<MallResponse<ShopModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull MallResponse<ShopModel> response) {

                        isAddSuccess = true;

                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                shopList.get(onePosition).getItems().get(position).setNumber((shopList.get(onePosition).getItems().get(position).getNumber() + 1));
                                shopAdapter.notifyDataSetChanged();
                                showCommodityCalculation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();

                        isAddSuccess = true;
                        XToastUtils.showRoundRectToast(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        isAddSuccess = true;
                    }
                });
    }


    /**
     * 购物车商品数量减少
     *
     * @param goodsId
     * @param onePosition
     * @param position
     */
    private void minusCart(String goodsId, int onePosition, int position) {
        ServerApi.getInstance().minusCart(getContext(), goodsId, "1", "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

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

                        isLessSucess = true;

                        if (CommonParametersUtils.checkToken(getContext(), response)) {
                            try {
                                shopList.get(onePosition).getItems().get(position).setNumber((shopList.get(onePosition).getItems().get(position).getNumber() - 1));
                                shopAdapter.notifyDataSetChanged();
                                showCommodityCalculation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();

                        XToastUtils.showRoundRectToast(e.getMessage());
                        isLessSucess = true;
                    }

                    @Override
                    public void onComplete() {
                        isLessSucess = true;
                    }
                });
    }


    @OnClick({R.id.checkbox_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox_all://全选按钮
                if (checkboxAll.isChecked()) {
                    for (int i = 0; i < shopList.size(); i++) {
                        shopList.get(i).setIscheck(true);
                        for (int j = 0; j < shopList.get(i).getItems().size(); j++) {
                            shopList.get(i).getItems().get(j).setIscheck(true);
                        }
                    }
                } else {
                    int length = shopList.size();
                    for (int i = 0; i < length; i++) {
                        shopList.get(i).setIscheck(false);
                        int lengthn = shopList.get(i).getItems().size();
                        for (int j = 0; j < lengthn; j++) {
                            shopList.get(i).getItems().get(j).setIscheck(false);
                        }
                    }
                }
                shopAdapter.notifyDataSetChanged();
                showCommodityCalculation();
                break;
        }
    }

    /**
     * 结算或删除逻辑处理
     */
    private void BillingOrDeleting() {
        if (isEditing) {//删除
            if (totalCheckedCount == 0) {
                XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_select_the_item_to_delete));
                return;
            }
            showDeleteDialog();
        } else {//结算
            if (totalCheckedCount == 0) {
                XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_select_the_item_to_be_settled));
                return;
            }

            String kuCunString = jianChaKuCun();
            judgeGoodsNumber(kuCunString);
        }
    }


    /**
     * 结算订单
     * 检查库存
     *
     * @param goods
     */
    private void judgeGoodsNumber(String goods) {
        ServerApi.getInstance().judgeGoodsNumber(getContext(), goods)
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
                        if (response.getCode() == 0) {
                            String selectedItemIds = getSelectedItemIds();
                            String str1 = "orders/order-confirm.html?goodIds=";
                            String str2 = "&type=1&t=";
                            String url = UrlConfig.getMallDomainBaseUrl() + str1 + selectedItemIds + str2;
                            DBMallH5Activity.openH5(getActivity(), url, "");
                        } else {
                            GetToast.useString(cnt, response.getMsg());
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
     * 确认删除弹窗
     */
    private void showDeleteDialog() {
        String from = String.format(cnt.getResources().getString(R.string.whether_to_remove_the_item),totalCheckedCount+"");
        DialogController.getInstance().showDBankDialog(cnt, ""
                , from
                , cnt.getResources().getString(R.string.determine), cnt.getResources().getString(R.string.string_cancel)
                , R.drawable.shape_shop_btn
                , new DBankDialog.OnSubmitClickListener() {
                    @Override
                    public void onSubmitClick() {
                        String selectedItemIds = getSelectedItemIds();
                        deleteCart(selectedItemIds);
                    }
                });
    }


    private String jianChaKuCun() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < shopList.size(); i++) {
            for (int j = 0; j < shopList.get(i).getItems().size(); j++) {
                if (shopList.get(i).getItems().get(j).isIscheck()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", shopList.get(i).getItems().get(j).getGoods_id());
                    jsonObject.put("num", shopList.get(i).getItems().get(j).getNumber());
                    jsonArray.add(jsonObject);
                }
            }
        }
        return jsonArray.toJSONString();
    }

    /**
     * 点击结算/删除
     * 获取选中的商品的id
     */
    private String getSelectedItemIds() {
        String ids = "";
        for (int i = 0; i < shopList.size(); i++) {
            for (int j = 0; j < shopList.get(i).getItems().size(); j++) {
                if (shopList.get(i).getItems().get(j).isIscheck()) {
                    ids = ids + shopList.get(i).getItems().get(j).getId() + ",";
                }
            }
        }
        return ids;
    }


    /**
     * adapter点击回调
     */
    private void setAdapterOnclicklistener() {
        /**
         * 全选
         */
        shopAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                shopList.get(position).setIscheck(isFlang);
                int length = shopList.get(position).getItems().size();
                for (int i = 0; i < length; i++) {
                    shopList.get(position).getItems().get(i).setIscheck(isFlang);
                }
                shopAdapter.notifyDataSetChanged();
                showCommodityCalculation();


                boolean isAllCheckBox = true;
                for (int i = 0; i < shopList.size(); i++) {
                    for (int j = 0; j < shopList.get(i).getItems().size(); j++) {
                        if (!shopList.get(i).getItems().get(j).isIscheck()) {
                            isAllCheckBox = false;
                        }
                    }
                }
                checkboxAll.setChecked(isAllCheckBox);
            }
        });

        /**
         * 计算选中商品价格
         */
        shopAdapter.setOnItemMoneyClickListener(new OnItemMoneyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCommodityCalculation();
            }

        });


        shopAdapter.setOnItemAddOrDeleteClickListener(new OnItemAddOrDeleteClickListener() {
            @Override
            public void onItemClick(View view, int onePosition, int position, int index, int num) {
                //index 1减  2加
                if (index == 1) {
                    if (num > 1) {
                        if (isLessSucess) {
                            isLessSucess = false;
                            minusCart(shopList.get(onePosition).getItems().get(position).getGoods_id(), onePosition, position);
                        }
                    } else {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.the_baby_can_be_reduced));
                    }
                } else {
                    if (isAddSuccess) {
                        isAddSuccess = false;
                        addCartNum(shopList.get(onePosition).getItems().get(position).getGoods_id(), onePosition, position);
                    }
                }

            }
        });


        shopAdapter.set_OnItemAllOnClickListener(new ShopAdapter.OnItemAllOnClickListener() {
            @Override
            public void onItemAllOnClickListener(View view, int positionItem, int position) {
                if (ClickOnUtils.isFastClickFiveHundred()) {
                    String goodDetailsUrl = shopList.get(positionItem).getItems().get(position).getGoodDetailsUrl();
                    DBMallH5Activity.openH5ForShop(getActivity(), goodDetailsUrl, "");
                }
            }
        });
    }

    /***
     * 计算商品的数量和价格
     */
    private void showCommodityCalculation() {
        totalPrice = "0";
        totalCheckedCount = 0;
        for (int i = 0; i < shopList.size(); i++) {
            for (int j = 0; j < shopList.get(i).getItems().size(); j++) {
                if (shopList.get(i).getItems().get(j).isIscheck()) {
                    totalPrice = StringUtils.moneyAddNewNum(totalPrice, StringUtils.moneyMultiplyNewNum(shopList.get(i).getItems().get(j).getPriceCurrency() + "", shopList.get(i).getItems().get(j).getNumber() + "",2),2);
                    totalCheckedCount++;
                } else {
                    checkboxAll.setChecked(false);
                }
            }
        }
        setBottomText();
    }


    /**
     * 设置
     * 1.底部选中商品统计金额是否显示
     * 2.底部按钮显示  删除/结算  切换
     */
    private void setBottomText() {
        try {
            if (totalCheckedCount > 0) {
                tvDetermine.setText(isEditing ? cnt.getResources().getString(R.string.delete) : cnt.getResources().getString(R.string.settlement) + "(" + totalCheckedCount + ")");
            } else {
                tvDetermine.setText(isEditing ? cnt.getResources().getString(R.string.delete) : cnt.getResources().getString(R.string.settlement) + "(0)");
            }

            if (isEditing) {
                tvAllMoney.setVisibility(View.GONE);
            } else {
                tvAllMoney.setVisibility(View.VISIBLE);
                tvAllMoney.setText(cnt.getResources().getString(R.string.he_ji_new,totalPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
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


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dbmall_shop;
    }

}
