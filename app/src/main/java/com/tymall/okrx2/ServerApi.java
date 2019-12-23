package com.tymall.okrx2;


import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;
import com.tymall.app.Constant;
import com.tymall.model.DBMallBannerModel;
import com.tymall.model.DBMallPageModel;
import com.tymall.model.GoodsListModel;
import com.tymall.model.GoodsListNewModel;
import com.tymall.model.HttpModel;



import com.tymall.model.ShopModel;
import com.tymall.utils.CacheUtils;
import com.tymall.utils.SP;
import com.tymall.utils.SharedPreferencesUtils;
import com.tymall.utils.SignUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * wds 2019.2.20
 */
public class ServerApi {

    private static  final  int SIZE = 10;

    String BaseURL = "http://yjlive.cn:8081";

    String  Login = BaseURL + "/api/single/home/login"; //登录
    String  Register = BaseURL + "/api/single/home/simpleReg"; //注册
    String  Banner = BaseURL + "/api/single/home/bannerList"; //Banner
    String  getGoodsMode = BaseURL + "/api/home/navList"; //商品分类
    String  getGoodsList = BaseURL + "/api/single/pms/goods/list"; //商品列表



    private static class SingletonHolder {

        private static final ServerApi INSTANCE = new ServerApi();

    }

    private ServerApi() {
    }

    public static final ServerApi getInstance() {

        return ServerApi.SingletonHolder.INSTANCE;


    }

    /**
     * 公共参数
     */
    private Map<String, String> addCommonParams(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("id", SharedPreferencesUtils.getInstance().getShared("userId"));
        params.put("token", SharedPreferencesUtils.getInstance().getShared("token"));
        String l = TextUtils.isEmpty(CacheUtils.getLanguage(context)) ? "" : CacheUtils.getLanguage(context);
        params.put("l", l);
        return params;

    }


    /**
     * 地产大富翁公共参数
     */
    private Map<String, String> addMonCommonParams(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("id", SharedPreferencesUtils.getInstance().getShared("userId"));
        params.put("token", SharedPreferencesUtils.getInstance().getShared("token"));
        String l = TextUtils.isEmpty(CacheUtils.getLanguage(context)) ? "" : CacheUtils.getLanguage(context);
        params.put("l", l);
        params.put("zoneId", SP.get(Constant.MON_ZONE_ID, ""));
        params.put("appkey", SP.get(Constant.MON_APP_KEY, ""));
        return params;

    }

    /**
     * 签名参数
     */
    private void sign(Map<String, String> params) {
        String timeStamps = SignUtil.Timestamps() + "";
        params.put("t", timeStamps);
        params.put("sign", SignUtil.getSortingValus(params, timeStamps));
    }

    /**
     * 返回UserId
     */
    private String getUserId() {
        return SharedPreferencesUtils.getInstance().getShared("userId");

    }

    public Observable<String> marketPay(Context context, String pid, String currency_id, String pay_password, String R2, String type) {
        Map<String, String> params = addCommonParams(context);
        params.put("currency_id", currency_id);
        params.put("pid", pid);
        params.put("pay_password", pay_password);
        params.put("R2", R2);
        sign(params);
        return OkGo.<String>post(HttpModel.marketPay)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }



    public Observable<String> login(Context context, String name, String pwd) {
        Map<String, String> params = addCommonParams(context);
        params.put("phone", name);
        params.put("password", pwd);
        sign(params);
        return OkGo.<String>post(Login)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }



    public Observable<String> register(Context context, String phone, String pass, String pass2 , String referee) {
        Map<String, String> params = addCommonParams(context);
        params.put("phone", phone);
        params.put("password", pass);
        params.put("confimpassword", pass2);
        params.put("invitecode", referee);
        sign(params);
        return OkGo.<String>post(Register)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }



    public Observable<MallResponse<List<DBMallBannerModel>>> getDBMallHomeBanner(Context context) {

        return OkGo.<MallResponse<List<DBMallBannerModel>>>get(Banner)
                .cacheKey(Banner+ getUserId())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<MallResponse<List<DBMallBannerModel>>>() {
                })
                .adapt(new ObservableBody<MallResponse<List<DBMallBannerModel>>>());
    }

    public Observable<MallResponse<List<DBMallPageModel>>> getGoodsMode(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", 1+"");
        params.put("pageSize", SIZE+"");
        sign(params);
        return OkGo.<MallResponse<List<DBMallPageModel>>>get(getGoodsMode)
                .params(params)
                .headers("'storeid'", "2")
                .headers("'authorization2'",CacheUtils.getDBMallToken())
                .cacheKey(getGoodsMode+ getUserId())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<MallResponse<List<DBMallPageModel>>>() {
                })
                .adapt(new ObservableBody<MallResponse<List<DBMallPageModel>>>());
    }

    public Observable<MallResponse<GoodsListNewModel>> categoryGoodlist(Context context, String page) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", SIZE+"");
        sign(params);
        return OkGo.<MallResponse<GoodsListNewModel>>get(getGoodsList)
                .params(params)
                .converter(new JsonConvert<MallResponse<GoodsListNewModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<GoodsListNewModel>>());
    }



    public Observable<MallResponse<GoodsListModel>> getDBMallGoodsList(Context context, String page) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page);
        sign(params);
        return OkGo.<MallResponse<GoodsListModel>>post(HttpModel.getDBMallGoodsList)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<GoodsListModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<GoodsListModel>>());
    }


    public Observable<MallResponse<GoodsListModel>> getDBMallGoodsListById(Context context, String page, String categoryId) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page);
        params.put("categoryId", categoryId);
        sign(params);
        return OkGo.<MallResponse<GoodsListModel>>post(HttpModel.getDBMallGoodsList)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<GoodsListModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<GoodsListModel>>());
    }

    public Observable<MallResponse<GoodsListModel>> querySearchResult(Context context, String page, String keyword) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page);
        params.put("keyword", keyword);
        sign(params);
        return OkGo.<MallResponse<GoodsListModel>>post(HttpModel.getDBMallGoodsList)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .tag("querySearchResult")
                .converter(new JsonConvert<MallResponse<GoodsListModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<GoodsListModel>>());
    }




    public Observable<String> queryCartCount(Context context) {
        Map<String, String> params = addCommonParams(context);
        sign(params);
        return OkGo.<String>post(HttpModel.queryCartCount)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }


    /**
     * 查询购物车数据
     *
     * @param context
     * @return
     */
    public Observable<MallResponse<ShopModel>> queryGetCartIndex(Context context) {
        Map<String, String> params = addCommonParams(context);
        sign(params);
        return OkGo.<MallResponse<ShopModel>>post(HttpModel.queryGetCartIndex)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
//                .cacheKey(HttpModel.queryGetCartIndex + getUserId())
//                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<MallResponse<ShopModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<ShopModel>>());
    }

    /**
     * 删除购物车选中商品
     *
     * @param context
     * @return
     */
    public Observable<MallResponse<ShopModel>> deleteCart(Context context, String ids) {
        Map<String, String> params = addCommonParams(context);
        params.put("ids", ids);
        sign(params);
        return OkGo.<MallResponse<ShopModel>>post(HttpModel.deleteCart)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<ShopModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<ShopModel>>());
    }


    public Observable<MallResponse<String>> judgeGoodsNumber(Context context, String goods) {
        Map<String, String> params = addCommonParams(context);
        params.put("goods", goods);
        sign(params);
        return OkGo.<MallResponse<String>>post(HttpModel.judgeGoodsNumber)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<String>>() {
                })
                .adapt(new ObservableBody<MallResponse<String>>());
    }


    /**
     * 购物车商品添加数量
     *
     * @param context
     * @param number
     * @return
     */
    public Observable<MallResponse<ShopModel>> addCart(Context context, String goodsId, String number) {
        Map<String, String> params = addCommonParams(context);
        params.put("goodsId", goodsId);
        params.put("number", number);
        sign(params);
        return OkGo.<MallResponse<ShopModel>>post(HttpModel.addCart)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<ShopModel>>() {
                })
                .adapt(new ObservableBody<MallResponse<ShopModel>>());
    }


    /**
     * 购物车商品减少数量
     *
     * @param context
     * @return
     */
    public Observable<MallResponse<String>> minusCart(Context context, String goodsId, String number, String productId) {
        Map<String, String> params = addCommonParams(context);
        params.put("goodsId", goodsId);
        params.put("number", number);
        params.put("productId", productId);
        sign(params);
        return OkGo.<MallResponse<String>>post(HttpModel.minusCart)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<String>>() {
                })
                .adapt(new ObservableBody<MallResponse<String>>());
    }


    /**
     * 获取R2
     *
     * @param context
     * @param orderId
     * @return
     */
    public Observable<MallResponse<String>> orderGetR2(Context context, String orderId) {
        Map<String, String> params = addCommonParams(context);
        params.put("orderId", orderId);
        sign(params);
        return OkGo.<MallResponse<String>>post(HttpModel.getOrderR2)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<String>>() {
                })
                .adapt(new ObservableBody<MallResponse<String>>());
    }


    /**
     * 获取订单列表
     *
     * @param context
     * @return
     */
    public Observable<String> getOrderList(Context context, int page, String status) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page + "");
        params.put("status", status);
        sign(params);
        return OkGo.<String>post(HttpModel.getOrderList)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }


    /**
     * 取消订单
     *
     * @param context
     * @param orderId
     * @return
     */
    public Observable<MallResponse<String>> cancelOrder(Context context, String orderId) {
        Map<String, String> params = addCommonParams(context);
        params.put("orderId", orderId);
        sign(params);
        return OkGo.<MallResponse<String>>post(HttpModel.cancelOrder)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<String>>() {
                })
                .adapt(new ObservableBody<MallResponse<String>>());
    }


    /**
     * 确认收货
     *
     * @param context
     * @param orderId
     * @return
     */
    public Observable<MallResponse<String>> confimAcceptOrder(Context context, String orderId) {
        Map<String, String> params = addCommonParams(context);
        params.put("orderId", orderId);
        sign(params);
        return OkGo.<MallResponse<String>>post(HttpModel.confimAcceptOrder)
                .headers("X-DS-Token", CacheUtils.getDBMallToken())
                .params(params)
                .converter(new JsonConvert<MallResponse<String>>() {
                })
                .adapt(new ObservableBody<MallResponse<String>>());
    }


    /**
     * 积分账户查询接口
     *
     * @param context
     * @return
     */
    public Observable<String> pointsAccount(Context context) {
        Map<String, String> params = addCommonParams(context);
        sign(params);
        return OkGo.<String>post(HttpModel.pointsAccount)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }


    /**
     * 积分账户操作记录接口
     *
     * @param context
     * @return
     */
    public Observable<String> pointsAccountLogs(Context context, String page) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page);
        sign(params);
        return OkGo.<String>post(HttpModel.pointsAccountLogs)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }


    /**
     * 积分账户积分记录接口
     *
     * @param context
     * @param page
     * @return
     */
    public Observable<String> pointsLogs(Context context, String page) {
        Map<String, String> params = addCommonParams(context);
        params.put("page", page);
        sign(params);
        return OkGo.<String>post(HttpModel.pointsLogs)
                .params(params)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }




    /**
     * 市场--智能量化和DBM理财
     *
     * @param context
     * @return
     */
    public Observable<String> fortuneProductsListForMarket(Context context) {
        Map<String, String> params = addCommonParams(context);
        sign(params);
        return OkGo.<String>post(HttpModel.fortuneProductsListForMarket)
                .params(params)
                .cacheKey(HttpModel.fortuneProductsListForMarket + getUserId())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<String>() {
                })
                .adapt(new ObservableBody<String>());
    }





}
