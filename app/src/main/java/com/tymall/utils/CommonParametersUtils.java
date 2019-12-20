package com.tymall.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.tymall.R;
import com.tymall.okrx2.MallResponse;
import com.tymall.okrx2.MonResponse;
import com.tymall.ui.login.LoginActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Created by zoujiamin on 2018/11/29.
 * OKGO 请求接口封装
 */

public class CommonParametersUtils {


    /**
     * 请求接口参数封装
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public static void postPackagingMethod(Context context, String url, Map<String, String> params, StringCallback callback) {
        String timeStamps = SignUtil.Timestamps() + "";
        params.put("id", SharedPreferencesUtils.getInstance().getShared("userId"));
        params.put("token", SharedPreferencesUtils.getInstance().getShared("token"));
        params.put("l", CacheUtils.getLanguage(context));
        params.put("t", timeStamps);
        String stringValue = SignUtil.getSortingValus(params, timeStamps);
        params.put("sign", stringValue);
        PostRequest<String> post = OkGo.post(url);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            post.params(key, params.get(key).toString());
        }
        post.execute((callback));
    }


    /**
     * 请求接口参数封装扩展----上传图片
     *
     * @param context
     * @param url
     * @param params
     * @param path
     * @param callback
     */
    public static void postPackagingMethodFile(Context context, String url, Map<String, String> params, String path, StringCallback callback) {
        params.put("id", SharedPreferencesUtils.getInstance().getShared("userId"));
        params.put("token", SharedPreferencesUtils.getInstance().getShared("token"));
        params.put("l", CacheUtils.getLanguage(context));
        PostRequest<String> post = OkGo.post(url);
        post.params("file", new File(path));
        Set<String> keys = params.keySet();
        for (String key : keys) {
            post.params(key, params.get(key).toString());
        }
        post.execute((callback));
    }


    /**
     * 验证返回结果
     * 针对java接口
     *
     * @param mContext
     * @param responseBody
     * @return
     */
    public static boolean validationResults(Context mContext, String responseBody) {
        if (!TextUtils.isEmpty(responseBody)) {
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                if (!jsonObject.optBoolean("success")) {
                    String message = jsonObject.optString("message");
                    if (!TextUtils.isEmpty(message)) {
                        XToastUtils.showRoundRectToast(message);

                    }
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        XToastUtils.showRoundRectToast(mContext.getResources().getString(R.string.string_no_konw_erro));
        return false;
    }



    /**
     * 验证返回结果
     * 针对PHP接口
     *
     * @param mContext
     * @param responseBody
     * @return
     */
    public static boolean response(Context mContext, String responseBody) {
        if (!TextUtils.isEmpty(responseBody)) {
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    return true;
                }
                String msg = jsonObject.optString("msg");
                if (code == 100 || code == 401) {
                    SharedPreferencesUtils.getInstance().clearUserShared();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    XToastUtils.showRoundRectToast(msg);
                    return false;
                }
                if (!TextUtils.isEmpty(msg)) {
                    XToastUtils.showRoundRectToast(msg);
                }
                return false;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        XToastUtils.showRoundRectToast(mContext.getResources().getString(R.string.string_no_konw_erro));
        return false;
    }

    /**
     * 验证返回结果
     * 针对PHP接口
     *
     * @param mContext
     * @param responseBody
     * @return
     */
    public static boolean validationResultsForPhp(Context mContext, String responseBody) {
        if (!TextUtils.isEmpty(responseBody)) {
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                int code = jsonObject.optInt("code");
                if (code == 1) {
                    return true;
                }
                String msg = jsonObject.optString("msg");
                if (code == 3) {
                    SharedPreferencesUtils.getInstance().clearUserShared();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    XToastUtils.showRoundRectToast(msg);
                    return false;
                }
                if (!TextUtils.isEmpty(msg)) {
                    XToastUtils.showRoundRectToast(msg);
                }
                return false;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        XToastUtils.showRoundRectToast(mContext.getResources().getString(R.string.string_no_konw_erro));
        return false;
    }


    /**
     * DBMall验证返回结果
     *
     * @return
     */
    public static boolean checkToken(Context mContext, MallResponse<?> response) {
        if (response != null && response.getCode() == 200) {
            return true;
        }
        try {
            if (response.getCode() == 401 || (response.getCode() == 100)) {//Token过期或被挤掉
                if (!TextUtils.isEmpty(response.getMsg())) {
                    XToastUtils.showRoundRectToast(response.getMsg());
                }
                LoginManager.getInstance().loginDBmall(mContext);
                return false;
            }
            if (!TextUtils.isEmpty(response.getMsg())) {
                XToastUtils.showRoundRectToast(response.getMsg());
                return  false;
            }

            if (response.getCode() == -1) {
                XToastUtils.showRoundRectToast(mContext.getString(R.string.string_no_konw_erro));
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean checkTokenNew(Context mContext, String response) {
        try {
            if (TextUtils.isEmpty(response)) {
                return false;
            }
            JSONObject jsonObject = new JSONObject(response);
            int errno = jsonObject.optInt("errno");
            if (errno == 0) {
                return true;
            }

            String errmsg = jsonObject.optString("errmsg");
            if (errno == 401) {//Token过期或被挤掉
                if (!TextUtils.isEmpty(errmsg)) {
                    XToastUtils.showRoundRectToast(errmsg);
                }
                LoginManager.getInstance().loginDBmall(mContext);
                return false;
            }
            if (!TextUtils.isEmpty(errmsg)) {
                XToastUtils.showRoundRectToast(errmsg);
            }

            if (errno == -1000) {
                XToastUtils.showRoundRectToast(mContext.getString(R.string.string_no_konw_erro));
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 大富翁验证返回结果
     *
     * @return
     */
    public static boolean checkMonResponse(Context mContext, MonResponse<?> response) {
        if (response != null && response.getCode() == 0) {
            return true;
        }
        try {
            if (response.getCode() == 401 || response.getCode() == 3) {//Token过期或被挤掉
                if (!TextUtils.isEmpty(response.msg)) {
                    XToastUtils.showRoundRectToast(response.msg);
                }
                LoginManager.getInstance().loginMon(mContext);
                return false;
            }

            if (response.getCode() == -1000) {
                XToastUtils.showRoundRectToast(mContext.getString(R.string.string_no_konw_erro));
            } else {
                if (!TextUtils.isEmpty(response.msg)) {
                    XToastUtils.showRoundRectToast(response.msg);
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static boolean checkMonResponseForString(Context mContext, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int code = jsonObject.optInt("code");
                if (code == 0) {
                    return true;
                }

                String msg = jsonObject.optString("msg");
                if (code == 401 || code == 3) {//Token过期或被挤掉
                    if (!TextUtils.isEmpty(msg)) {
                        XToastUtils.showRoundRectToast(msg);
                    }
                    LoginManager.getInstance().loginMon(mContext);
                    return false;
                }
                if (code == -1000) {
                    XToastUtils.showRoundRectToast(mContext.getString(R.string.string_no_konw_erro));
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        XToastUtils.showRoundRectToast(msg);
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;

    }

}


