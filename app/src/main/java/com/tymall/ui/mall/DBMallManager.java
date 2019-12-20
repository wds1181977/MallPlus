package com.tymall.ui.mall;


import android.content.Context;
import android.text.TextUtils;

import com.tymall.R;
import com.tymall.base.BaseActivity;
import com.tymall.model.HttpModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.ServerApi;
import com.tymall.utils.CacheUtils;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.LoginManager;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import org.json.JSONObject;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DBMallManager implements Serializable {

    private static class SingletonHolder {

        private static final DBMallManager INSTANCE = new DBMallManager();

    }

    private DBMallManager() {
    }

    public static final DBMallManager getInstance() {

        return DBMallManager.SingletonHolder.INSTANCE;


    }

    public static class DBMallPayParams implements Serializable {


        private String pid;
        private String currency_id;
        private String pay_password;
        private String orderId;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String type;

        private String amount;

        private String actualPrice;
        private String symbol;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }


        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }


        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPay_password() {
            return pay_password;
        }

        public void setPay_password(String pay_password) {
            this.pay_password = pay_password;
        }


        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }


        public DBMallPayParams pId(String p) {
            this.pid = p;
            return this;
        }

        public DBMallPayParams currencyId(String c) {
            this.currency_id = c;
            return this;
        }


        public DBMallPayParams payPassword(String pwd) {
            this.pay_password = pwd;
            return this;
        }

        public DBMallPayParams orderId(String oId) {
            this.orderId = oId;
            return this;
        }

        public DBMallPayParams actualPrice(String act) {
            this.actualPrice = act;
            return this;
        }

        public DBMallPayParams symbol(String sy) {
            this.symbol = sy;
            return this;
        }

        public DBMallPayParams amount(String am) {
            this.amount = am;
            return this;
        }

        public DBMallPayParams type(String t) {
            this.type = t;
            return this;
        }

    }

    public void makePayMarket(Context context, DBMallManager.DBMallPayParams params) {


        if (context == null || params.orderId == null || params.currency_id == null || params.pid == null || params.pay_password == null) {
            XToastUtils.showRoundRectToast(context.getString(R.string.string_eos_tx_error));
            return;
        }
        BaseActivity baseActivity = (BaseActivity) context;
        baseActivity.showLoading();
        ServerApi.getInstance().orderGetR2(context, params.orderId)
                .flatMap(new Function<MallResponse<String>, Observable<String>>() {
                    @Override
                    public Observable<String> apply(MallResponse<String> response) throws Exception {
                        if (CommonParametersUtils.checkToken(context, response)) {
                            String R2 = response.getData();
                            if(TextUtils.isEmpty(R2)){
                                return Observable.empty();
                            }
                            return ServerApi.getInstance().marketPay(context, params.pid, params.currency_id, params.pay_password, R2,params.type);

                        } else {
                            return Observable.empty();
                        }

                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                      baseActivity.addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            int code = obj.optInt("code");
                            String msg = obj.getString("msg");
                            if (code == 1) {
                                String orderId = obj.getString("data");
                                if (!TextUtils.isEmpty(orderId)) {
                                    String token = CacheUtils.getDBMallToken();
                                    String url = new StringBuilder(HttpModel.getMarketPaySuccess)
                                            .append("?t=" + token).append("&o=" + orderId).toString();
                                    DBMallH5Activity.openH5NoToken(baseActivity, url);
                                }
                            } else if (code == 3) {
                                LoginManager.getInstance().loginDBmall(context);
                            } else {
                                if (!TextUtils.isEmpty(msg)) {
                                    ToastUtils.show(msg);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        XToastUtils.showRoundRectToast(e.getMessage());
                        baseActivity.hideLoading();

                    }

                    @Override
                    public void onComplete() {
                        baseActivity.hideLoading();


                    }
                });
    }

}
