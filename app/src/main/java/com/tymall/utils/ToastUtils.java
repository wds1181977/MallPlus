package com.tymall.utils;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.tymall.app.MyApplication;

public class ToastUtils {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    public static void show(int msgRes){
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(msgRes);
            }
        });

    }
    public static void show(String msg) {
        if(TextUtils.isEmpty(msg)){
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToastMsg(msg);
            }
        });
    }

    public static Toast mToast = null;

    private static void showToast(int msgRes) {
        if(TextUtils.isEmpty(MyApplication.getInstance().getString(msgRes))){
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(),MyApplication.getInstance().getString(msgRes), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(MyApplication.getInstance().getString(msgRes));
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    private static void showToastMsg(String msgRes) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(),msgRes, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgRes);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 关闭Toast cxj release
     */
    public static void cancelToast(){
        if(mToast !=null){
            mToast.cancel();
            mToast = null;
        }
    }
}
