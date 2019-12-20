package com.tymall.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by zoujiamin on 2018/12/10.
 */

public class GetToast {
    public static void useString(Context context, String string) {
        if (context == null)
            return;
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }


    /**
     * 长Toast
     * 且位置在中间
     *
     * @param context
     * @param string
     */
    public static void useStringLong(Context context, String string) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void useid(Context context, int id) {
        if (context == null)
            return;
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();

    }

    /***实现单例的Toast方法***/
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }


    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }
}
