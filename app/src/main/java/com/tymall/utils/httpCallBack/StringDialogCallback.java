/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tymall.utils.httpCallBack;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aries.ui.widget.progress.UIProgressDialog;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.utils.GetToast;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public abstract class StringDialogCallback extends StringCallback {

    private UIProgressDialog dialog;

    protected UIProgressDialog createProgressDialog(Context context,String title, String content) {
        String message = context.getString(R.string.loading_msg);

        UIProgressDialog dialog = new UIProgressDialog.MaterialBuilder(context)
                .setMessage(TextUtils.isEmpty(content) ? message : content)
                .setBackgroundRadiusResource(R.dimen.dp_radius_loading)
                .create();
        dialog.setTitle(TextUtils.isEmpty(title) ? "" : title);
        dialog.setDimAmount(0.6f);
        return dialog;

    }


    public void dismissProgress() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                        dialog = null;
                }
            }, Constant.DISMISS_TIME);



        }catch (Exception e){

        }
    }

    private Activity activity;

    public StringDialogCallback() {

    }

    public StringDialogCallback(Activity activity) {
        this.activity = activity;
        if (dialog != null && dialog.isShowing())
            return;
        if (dialog == null) {
            dialog = createProgressDialog(activity,"", "");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);

        }

    }




    @Override
    public void onStart(Request<String, ? extends Request> request) {
        if (dialog != null)
            dialog.show();
    }


    @Override
    public void onSuccess(Response<String> response) {
        dismissProgress();
    }

    @Override
    public void onFinish() {
        dismissProgress();
    }

    @Override
    public void onError(Response<String> response) {
        dismissProgress();
        super.onError(response);
        JSONObject jsonObject = null;
        try {
            if (!TextUtils.isEmpty(response.getRawResponse().body().string())) {
                jsonObject = JSON.parseObject(response.getRawResponse().body().string());
                String message = activity.getString(R.string.network_request_data_is_abnormal);
                if(jsonObject != null) {
                     message = jsonObject.getString("message");
                }

                if (!TextUtils.isEmpty(message)) {
                    GetToast.useString(activity, message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
