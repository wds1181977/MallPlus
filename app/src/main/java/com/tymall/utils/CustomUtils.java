package com.tymall.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tymall.app.MyApplication;

import java.util.List;

// 单例模式-静态内部类
public class CustomUtils {
    // 将构造函数私有化
    private CustomUtils() {
    }

    // 静态内部类
    private static class CustomUtilsInstance {
        private static final CustomUtils instance = new CustomUtils();

    }

    public static CustomUtils getInstance() {
        return CustomUtilsInstance.instance;
    }

    private static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    public DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return outMetrics;
    }

    /**
     * dp转换px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * PX转DIP
     *
     * @param value
     * @return
     */
    public static int px2dip(int value) {
        float dipScale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value / dipScale + 0.5f);
    }

    public static void doClip(String content) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE); //创建ClipData对象
        ClipData clipData = ClipData.newPlainText(null, content); //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);

    }

    /**
     * 获取app版本号
     */
    public static String getVersion(Context context) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    /**
     * 获取app版本号
     */
    public static int getVersionCode(Context context) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packageInfo.versionCode;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 转账地址检查
     *
     * @param address
     * @return
     */
    public static boolean checkAddress(String address) {
        if (TextUtils.isEmpty(address) || !address.startsWith("0x") || address.length() != 42) {
            return false;
        }
        return true;
    }

//    public static boolean checkAddress(String address) {
//        if ("".equals(address) || address.length() < 42 || !"0x".equals(address.substring(0, 2))) {
//            return false;
//        }
//        return true;
//    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int hour;//小时
        int minute;//分钟
        int second;//秒数
        hour = (int) ((millisecond / 1000) / 60 / 60);
        minute = (int) ((millisecond / 1000) / 60 % 60);
        second = (int) ((millisecond / 1000) % 60);
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":" + "0" + minute + ":" + "0" + second;
                } else {
                    return "0" + hour + ":" + "0" + minute + ":" + second;
                }
            } else {
                if (second < 10) {
                    return "0" + hour + ":" + minute + ":" + "0" + second;
                } else {
                    return "0" + hour + ":" + minute + ":" + second;
                }
            }
        } else {
            if (minute < 10) {
                if (second < 10) {
                    return hour + ":" + "0" + minute + ":" + "0" + second;
                } else {
                    return hour + ":" + "0" + minute + ":" + second;
                }
            } else {
                if (second < 10) {
                    return hour + ":" + minute + ":" + "0" + second;
                } else {
                    return hour + ":" + minute + ":" + second;
                }
            }
        }
    }

    // 将px值转换为sp值
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将px值转换为dip或dp值
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }
}
