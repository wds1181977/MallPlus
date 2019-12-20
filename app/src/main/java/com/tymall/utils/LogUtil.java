package com.tymall.utils;

import android.util.Log;

import com.tymall.app.MyApplication;

/**
 * Created by zoujiamin on 2018/11/29.
 */

public class LogUtil {
    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (MyApplication.isLog)
            Log.i(MyApplication.TAG_LOG, msg);
    }

    public static void d(String msg) {
        if (MyApplication.isLog)
            Log.d(MyApplication.TAG_LOG, msg);
    }

    public static void e(String msg) {
        if (MyApplication.isLog)
            Log.e(MyApplication.TAG_LOG, msg);
    }

    public static void v(String msg) {
        if (MyApplication.isLog)
            Log.v(MyApplication.TAG_LOG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (MyApplication.isLog)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (MyApplication.isLog)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (MyApplication.isLog)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (MyApplication.isLog)
            Log.v(tag, msg);
    }
}
