package com.tymall.utils.httpCallBack;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tymall.utils.ToastUtils;


/**
 * Created by zoujiamin on 2018/6/14.
 */

public abstract class MyStringCallback extends StringCallback {
    private Context cnt;

    public MyStringCallback() {
        super();
    }

    public MyStringCallback(Context cnt) {
        super();
        this.cnt = cnt;
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(response.getRawResponse().body().string());
            String message = jsonObject.getString("message");
            if (!TextUtils.isEmpty(message)) {
                ToastUtils.show(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
