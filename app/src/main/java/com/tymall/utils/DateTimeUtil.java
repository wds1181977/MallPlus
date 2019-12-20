package com.tymall.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zoujiamin on 2018/11/28.
 */

public class DateTimeUtil {

    public static String format0(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String dateTime = df.format(new Date(new Long(time) * 1000));
        return dateTime;
    }


    public static String format1(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm");
        String dateTime = format.format(new Date(new Long(time) * 1000));
        return dateTime;
    }

    public static String format2(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String dateTime = format.format(new Date(new Long(time) * 1000));
        return dateTime;
    }

    public static String format3(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = df.format(new Date(new Long(time) * 1000));
        return dateTime;
    }

    public static long getSystemStamp() {
        //获取当前时间戳
        return System.currentTimeMillis();


    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDateNew(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
