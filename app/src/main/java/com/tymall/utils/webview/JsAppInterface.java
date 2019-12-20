package com.tymall.utils.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.tymall.ui.mall.DBMallH5Activity;

import org.json.JSONObject;

import java.lang.reflect.Constructor;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class JsAppInterface {

    private Context mContext;
    private ScrollWebView mWebView;

    public JsAppInterface(Context context, ScrollWebView mWebView) {
        this.mContext = context;
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public String jsToAppInterface(String valJson) {
        if (mContext instanceof DBMallH5Activity) {
            if (!((DBMallH5Activity) (mContext)).isJsToAppCallBack) {
                return "";
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(valJson);
            String method = jsonObject.optString("method");
            String data = jsonObject.optString("data");
            String callbackId = jsonObject.optString("callbackId");
            routePageNew(method, data, callbackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void routePageNew(String method, String data, String callbackId) {
        Class<?> clazz = null;
        try {
            //返回与具有给定字符串名称的类或接口关联的{@code Class}对象。
            //调用此方法等效于：Class.forName(className，true，currentLoader)
            clazz = Class.forName("com.ycbjie.library.web.js.JsUtils");
            Constructor constructor = clazz.getDeclaredConstructor(Context.class, ScrollWebView.class);
            //设置安全检查，访问私有构造函数必须
            constructor.setAccessible(true);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
