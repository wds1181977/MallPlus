package com.tymall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tymall.app.MyApplication;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;
    public static String SHARED_PREFS_NAME = "User";
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPreferencesUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized SharedPreferencesUtils getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesUtils(MyApplication.getContext().getApplicationContext());
        }
        return instance;
    }

    //本地存储方法
    public void addShared(String key, String value) {
        //editor->添加并提交内容以及clear
        //添加要保存内容
        editor.putString(key, value);
        //提交要保存内容
        editor.commit();
    }

    //本地获取方法
    public String getShared(String key) {
        //按照传入key获取value如果key不存在返回参数二添加的默认内容
        String result = mSharedPreferences.getString(key, "-1");
        return result;
    }

    //本地清空方法
    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    //清空用户系统数据
    public void clearUserShared() {
        editor.remove("userId");
        editor.remove("userName");
        editor.remove("token");
        editor.commit();
    }


    //清空DBMall Token
    public void clearDBMallShared() {
        editor.remove("dbMallToken");
        editor.commit();
    }

    //清空大富翁
    public void clearMonShared() {
        editor.remove("monToken");
        editor.commit();
    }

    //清空指定数据
    public void clearShared(String key) {
        editor.remove(key);
        editor.commit();
    }


    /**
     * ETH
     */
    public void clearWalletAddree() {
        editor.remove("walletAddr");
        editor.commit();
    }

    public void clearWalletBackUp() {
        editor.remove("backUpWallet");
        editor.commit();
    }


    public void clearEthWalletTotalAmountCn() {
        editor.remove("totalAmountCn");
        editor.commit();
    }

    public void clearEthWalletTotalAmountEn() {
        editor.remove("totalAmountEn");
        editor.commit();
    }


    /**
     * BTC
     */
    public void clearBtcWalletAddree() {
        editor.remove("btcWalletAddr");
        editor.commit();
    }

    public void clearBtcWalletBackUp() {
        editor.remove("btcBackUpWallet");
        editor.commit();
    }

    public void clearBtcWalletTotalAmountCn() {
        editor.remove("btcTotalAmountCn");
        editor.commit();
    }

    public void clearBtcWalletTotalAmountEn() {
        editor.remove("btcTotalAmountEn");
        editor.commit();
    }
}
