package com.tymall.utils;

import android.content.Context;
import android.text.TextUtils;

import com.aries.ui.widget.progress.UIProgressDialog;
import com.tymall.R;

public class CustomDialog {
    private static CustomDialog getInstance;
    private static UIProgressDialog dialog;
    private Context mContext;

    private CustomDialog(Context context) {
        this.mContext = context;
    }

    public static synchronized CustomDialog getInstance(Context context) {
        if (getInstance == null) {
            getInstance = new CustomDialog(context);
        }
        return getInstance;
    }

    public void setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void setCancelable(boolean flag) {
        dialog.setCancelable(flag);
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void setContent(String content) {

    }

    public void show(String content) {

        if (dialog == null) {
            dialog = createProgressDialog(mContext, "", content);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
    }


    protected UIProgressDialog createProgressDialog(Context context, String title, String content) {
        String message = context.getString(R.string.loading_msg);

        UIProgressDialog dialog = new UIProgressDialog.MaterialBuilder(context)
                .setMessage(TextUtils.isEmpty(content) ? message : content)
                .setBackgroundRadiusResource(R.dimen.dp_radius_loading)
                .create();
        dialog.setTitle(TextUtils.isEmpty(title) ? "" : title);
        dialog.setDimAmount(0.6f);
        return dialog;

    }

    public void show() {

        if (dialog == null) {
            dialog = createProgressDialog(mContext, "", "");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    public void dismiss() {
        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
                dialog = null;


        } catch (Exception e) {

        }
    }

    public void destoryDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
        getInstance = null;
        mContext = null;
    }
}
