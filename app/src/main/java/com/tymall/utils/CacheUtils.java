package com.tymall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by zoujiamin on 2018/11/29.
 * SharedPreferences缓存
 */

public class CacheUtils {
    /**
     * 语言设置
     */
    private static final String LANGUAGE_SETTINGS = "language_settings";

    static SharedPreferences getSharedPreferences(final Context mContext) {
        if (mContext != null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext);
        } else {
            return null;
        }
    }


    /**
     * 语言设置
     *
     * @param mContext
     * @param language
     */
    public static void setLanguage(final Context mContext, final String language) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putString(LANGUAGE_SETTINGS, language);
        editor.commit();
    }

    public static String getLanguage(final Context ctx) {
        if (getSharedPreferences(ctx) != null) {
            return getSharedPreferences(ctx).getString(LANGUAGE_SETTINGS, "");
        } else {
            return null;
        }
    }


    /**
     * 是否是中文模式
     *
     * @param context
     * @return
     */
    public static boolean isChineseLanguage(Context context) {
        if ("cn".equals(getLanguage(context))) {
            return true;
        }
        return false;
    }

    /**
     * 是否是英文文模式
     *
     * @param context
     * @return
     */
    public static boolean isEnglishLanguage(Context context) {
        if ("en".equals(getLanguage(context))) {
            return true;
        }
        return false;
    }


    /**
     * 是否是韩文文模式
     *
     * @param context
     * @return
     */
    public static boolean isKoreaLanguage(Context context) {
        if ("ko".equals(getLanguage(context))) {
            return true;
        }
        return false;
    }


    /**
     * 获取钱包地址
     *
     * @return
     */
    public static String getWallAddress() {
        String walletAdress = SharedPreferencesUtils.getInstance().getShared("walletAddr");
        return walletAdress;
    }


    public static void setWallAddress(String walletAddress) {
        SharedPreferencesUtils.getInstance().addShared("walletAddr", walletAddress);
    }


    /**
     * ETH 是否备份过钱包
     *
     * @param backUpWallet
     */
    public static void setBackupWallet(String backUpWallet) {
        SharedPreferencesUtils.getInstance().addShared("backUpWallet", backUpWallet);
    }

    public static String getBackUpWallet() {
        String backUpWallet = SharedPreferencesUtils.getInstance().getShared("backUpWallet");
        return backUpWallet;
    }


    /**
     * BTC钱包地址
     *
     * @return
     */
    public static String getBtcWallAddress() {
        String btcwalletAdress = SharedPreferencesUtils.getInstance().getShared("btcWalletAddr");
        return btcwalletAdress;
    }


    public static void setBtcWallAddress(String btcWalletAddress) {
        SharedPreferencesUtils.getInstance().addShared("btcWalletAddr", btcWalletAddress);
    }


    /**
     * BTC是否备份过钱包
     *
     * @param btcBackUpWallet
     */
    public static void setBtcBackupWallet(String btcBackUpWallet) {
        SharedPreferencesUtils.getInstance().addShared("btcBackUpWallet", btcBackUpWallet);
    }

    public static String getBtcBackUpWallet() {
        String backUpWallet = SharedPreferencesUtils.getInstance().getShared("btcBackUpWallet");
        return backUpWallet;
    }


    /**
     * ETH
     * 首页总金额是否显示
     * 0显示
     * 1不显示
     * 默认显示
     *
     * @param display
     */
    public static void setMainMoneydisplay(String display) {
        SharedPreferencesUtils.getInstance().addShared("display", display);

    }

    public static String getMainMoneydisplay() {
        String backUpWallet = SharedPreferencesUtils.getInstance().getShared("display");
        return backUpWallet;
    }


    /**
     * BTC
     * 首页总金额是否显示
     * 0显示
     * 1不显示
     * 默认显示
     *
     * @param btcDisplay
     */
    public static void setBtcMoneydisplay(String btcDisplay) {
        SharedPreferencesUtils.getInstance().addShared("btcDisplay", btcDisplay);

    }

    public static String getBtcMoneydisplay() {
        String btcDisplay = SharedPreferencesUtils.getInstance().getShared("btcDisplay");
        return btcDisplay;
    }


    /**
     * EOS
     * 首页总金额是否显示
     * 0显示
     * 1不显示
     * 默认显示
     *
     * @param eosDisplay
     */
    public static void setEosMoneydisplay(String eosDisplay) {
        SharedPreferencesUtils.getInstance().addShared("eosDisplay", eosDisplay);

    }

    public static String getEosMoneydisplay() {
        String eosDisplay = SharedPreferencesUtils.getInstance().getShared("eosDisplay");
        return eosDisplay;
    }


    /**
     * 银行页面 资产是否显示
     * 0显示
     * 1隐藏
     *
     * @param bankDisplay
     */
    public static void setBankMoneydisplay(String bankDisplay) {
        SharedPreferencesUtils.getInstance().addShared("bankDisplay", bankDisplay);

    }

    public static String getBankMoneydisplay() {
        String bankDisplay = SharedPreferencesUtils.getInstance().getShared("bankDisplay");
        return bankDisplay;
    }


    /**
     * ETH 人民币价值总额
     *
     * @param totalAmountCn
     */
    public static void setMainTotalAmountCn(String totalAmountCn) {
        SharedPreferencesUtils.getInstance().addShared("totalAmountCn", totalAmountCn);

    }

    public static String getMainTotalAmountCn() {
        String totalAmountCn = SharedPreferencesUtils.getInstance().getShared("totalAmountCn");
        return totalAmountCn;
    }


    /**
     * ETH  美元价值总额
     *
     * @param totalAmountEn
     */
    public static void setMainTotalAmountEn(String totalAmountEn) {
        SharedPreferencesUtils.getInstance().addShared("totalAmountEn", totalAmountEn);

    }

    public static String getMainTotalAmountEn() {
        String backUpWallet = SharedPreferencesUtils.getInstance().getShared("totalAmountEn");
        return backUpWallet;
    }


    /**
     * BTC 人民币价值总额
     *
     * @param btcTotalAmountCn
     */
    public static void setBtcTotalAmountCn(String btcTotalAmountCn) {
        SharedPreferencesUtils.getInstance().addShared("btcTotalAmountCn", btcTotalAmountCn);

    }

    public static String getBtcTotalAmountCn() {
        String btcTotalAmountCn = SharedPreferencesUtils.getInstance().getShared("btcTotalAmountCn");
        return btcTotalAmountCn;
    }


    /**
     * ETH  美元价值总额
     *
     * @param btcTotalAmountEn
     */
    public static void setBtcTotalAmountEn(String btcTotalAmountEn) {
        SharedPreferencesUtils.getInstance().addShared("btcTotalAmountEn", btcTotalAmountEn);

    }

    public static String getBtcTotalAmountEn() {
        String btcTotalAmountEn = SharedPreferencesUtils.getInstance().getShared("btcTotalAmountEn");
        return btcTotalAmountEn;
    }


    /**
     * EOS人民币价值总额
     *
     * @param eosTotalAmountCn
     */
    public static void setEosTotalAmountCn(String eosTotalAmountCn) {
        SharedPreferencesUtils.getInstance().addShared("eosTotalAmountCn", eosTotalAmountCn);

    }

    public static String getEosTotalAmountCn() {
        String btcTotalAmountCn = SharedPreferencesUtils.getInstance().getShared("eosTotalAmountCn");
        return btcTotalAmountCn;
    }


    /**
     * EOS 美元价值总额
     *
     * @param eosTotalAmountEn
     */
    public static void setEosTotalAmountEn(String eosTotalAmountEn) {
        SharedPreferencesUtils.getInstance().addShared("eosTotalAmountEn", eosTotalAmountEn);

    }

    public static String getEosTotalAmountEn() {
        String eosTotalAmountEn = SharedPreferencesUtils.getInstance().getShared("eosTotalAmountEn");
        return eosTotalAmountEn;
    }


    /**
     * DBMall  Token
     *
     * @return
     */
    public static String getDBMallToken() {
        String btcwalletAdress = SharedPreferencesUtils.getInstance().getShared("dbMallToken");
        return btcwalletAdress;
    }


    public static void setDBMallToken(String dbMallToken) {
        if (TextUtils.isEmpty(dbMallToken)) {
            return;
        }
        SharedPreferencesUtils.getInstance().addShared("dbMallToken", dbMallToken);
    }

    /**
     * @description set 大富翁Token
     */
    public static void setMonToken(String monToken) {
        if (TextUtils.isEmpty(monToken)) {
            return;
        }
        SharedPreferencesUtils.getInstance().addShared("monToken", monToken);
    }


    /**
     * DBMall   get 大富翁Token
     *
     * @return
     */
    public static String getMonToken() {
        String monToken = SharedPreferencesUtils.getInstance().getShared("monToken");
        return monToken;
    }


    /**
     * 是否刷新购物车
     * 标记：301
     *
     * @return
     */
    public static String getIsRefreshShoppingCart() {
        String shoppingCart = SharedPreferencesUtils.getInstance().getShared("shoppingCart");
        return shoppingCart;
    }


    public static void setIsRefreshShoppingCart(String shoppingCart) {
        SharedPreferencesUtils.getInstance().addShared("shoppingCart", shoppingCart);
    }

    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 音效开关
     * 1 开启
     * 2 关闭
     *
     * @param sound
     */
    public static void setSoundSwitch(String sound) {
        SharedPreferencesUtils.getInstance().addShared("sound", sound);

    }

    public static String getSoundSwitch() {
        String sound = SharedPreferencesUtils.getInstance().getShared("sound");
        return sound;
    }
}
