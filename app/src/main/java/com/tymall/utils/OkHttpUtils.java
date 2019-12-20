package com.tymall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tymall.R;
import com.tymall.app.MyApplication;
import com.tymall.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    private NetCallBack mNetCallBack;// 回调给MainActivity传递网络请回来的String
    private Context mContext;
    private String mUrl;
    private Map<String, String> mMapParams;
    private MultipartBody.Builder mBuilder;

    public OkHttpUtils(Context mContext, String mUrl, Map<String, String> mMapParams, NetCallBack mNetCallBack) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.mMapParams = mMapParams;
        this.mNetCallBack = mNetCallBack;
    }

    public OkHttpUtils(Context mContext, String mUrl, MultipartBody.Builder mBuilder, NetCallBack mNetCallBack) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.mBuilder = mBuilder;
        this.mNetCallBack = mNetCallBack;
    }

    private static OkHttpClient client = null;

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpUtils.class) {
                if (client == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    if(!MyApplication.isCanCatchBag) {
                        builder.connectTimeout(5, TimeUnit.MINUTES)
                                .writeTimeout(5, TimeUnit.MINUTES)
                                .readTimeout(5, TimeUnit.MINUTES)
                                .proxySelector(new ProxySelector() {  //防止抓包
                                    @Override
                                    public List<Proxy> select(URI uri) {

                                        return Collections.singletonList(Proxy.NO_PROXY);
                                    }

                                    @Override
                                    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

                                    }
                                });
                    }else {

                        builder.connectTimeout(5, TimeUnit.MINUTES)
                                .writeTimeout(5, TimeUnit.MINUTES)
                                .readTimeout(5, TimeUnit.MINUTES);


                    }

                    client = builder.build();
                }
            }
        }
        return client;
    }

    public void doPost() {
        LogUtil.i("url==" + mUrl);
        String timeStamps = SignUtil.Timestamps() + "";
        FormBody.Builder builder = new FormBody.Builder();
        mMapParams.put("l", CacheUtils.getLanguage(mContext));
        mMapParams.put("id", SharedPreferencesUtils.getInstance().getShared("userId"));
        mMapParams.put("token", SharedPreferencesUtils.getInstance().getShared("token"));
        mMapParams.put("t", timeStamps);
        String stringValue = SignUtil.getSortingValus(mMapParams, timeStamps);
        mMapParams.put("sign", stringValue);
        for (String key : mMapParams.keySet()) {
            builder.add(key, mMapParams.get(key) + "");
            LogUtil.i("key=" + key + ",value==" + mMapParams.get(key) + "\n");
        }
        Request request = new Request.Builder()
                .url(mUrl)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("onFailure------------000000" + e.toString() + "," + e.getMessage());
                mNetCallBack.httpCallbackFailed(e.getMessage() + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtil.i("urls=" + mUrl + "--分割线--response==" + result);
                try {
                    JSONObject obj = new JSONObject(result);
                    int code = obj.optInt("code");
                    if (3 == code) {
                        SharedPreferencesUtils.getInstance().clearUserShared();
                        Activity activity = (Activity) mContext;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GetToast.useString(mContext, mContext.getResources().getString(R.string.please_login_to_operate));
                            }
                        });
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } else {
                        mNetCallBack.httpCallback(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface NetCallBack {
        void httpCallback(String result) throws JSONException;

        void httpCallbackFailed(String result);
    }
}
