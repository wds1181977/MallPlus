package com.tymall.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zoujiamin on 2018/12/11.
 */

public class StringUtils {


    /**
     * 截取地址  显示为：前7位后7位 中间....
     *
     * @param address
     * @return
     */
    public static String addressSeven(String address) {
        if (TextUtils.isEmpty(address)) {
            return "";
        }
        if (address.length() <= 14) {
            return address;
        }
        String substringHead = address.substring(0, 7);
        String substringTail = address.substring(address.length() - 7, address.length());
        String newAddress = substringHead + "..." + substringTail;
        return newAddress;
    }


    /**
     * 截取地址  显示为 前10位后10位  中间...
     *
     * @param address
     * @return
     */
    public static String addressTen(String address) {
        if (TextUtils.isEmpty(address) || address.length() < 20) {
            return "";
        }
        String substringHead = address.substring(0, 10);
        String substringTail = address.substring(address.length() - 10, address.length());
        String newAddress = substringHead + "..." + substringTail;
        return newAddress;
    }


    /**
     * 把数字转换为几位小数
     *
     * @param str
     * @return
     */
    public static String bigDecimalNum(String str, int num) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        BigDecimal bg = new BigDecimal(str);
        BigDecimal bigDecimal = bg.setScale(num, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }


    /**
     * 去掉小数后边多余的0
     *
     * @param s
     * @return
     */
//    public static String trimZero(String s) {
//        if (TextUtils.isEmpty(s)) {
//            return s;
//        }
//        if (s.indexOf(".") > 0) {
//            // 去掉多余的0
//            s = s.replaceAll("0+?$", "");
//            // 如最后一位是.则去掉
//            s = s.replaceAll("[.]$", "");
//        }
//        return s;
//    }


    /**
     * java方法判断字符串是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 正则表达式的方法判断字符串是否为整数
     *
     * @param str
     * @return
     */
    public boolean isNumericNew(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 金额计算  加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String moneyAdd(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimalAdd.toString();
    }

    /**
     * 金额计算  加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String moneyAddNew(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2).setScale(0, BigDecimal.ROUND_HALF_UP);
        return bigDecimalAdd.toString();
    }


    /**
     * 金额计算 加法
     * 自定义保留几位小数
     * 直接截断、不四舍五入
     *
     * @param d1
     * @param d2
     * @param precision
     * @return
     */
    public static String moneyAddNewNum(String d1, String d2, int precision) {
        if (TextUtils.isEmpty(d1) || TextUtils.isEmpty(d2)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2);
        BigDecimal bigDecimalEnd = bigDecimalAdd.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
        return bigDecimalEnd.stripTrailingZeros().toPlainString();
    }


    /**
     * 金额计算  减法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String moneySubtract(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalSubtract = bigDecimal.subtract(bigDecimal2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimalSubtract.toString();
    }


    /**
     * 金额计算 乘法
     *
     * @param d1
     * @param d2 scale   保留几位小数
     * @return
     */
    public static String moneyMultiply(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
        BigDecimal bigDecimal1 = bigDecimalMultiply.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal1.toString();
    }


    public static String moneyMultiplyNew(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
        BigDecimal bigDecimal1 = bigDecimalMultiply.setScale(0, BigDecimal.ROUND_HALF_UP);
        return bigDecimal1.toString();
    }


    /**
     * 金额计算 乘法
     * 自定义保留几位小数
     * 直接截断、不四舍五入
     *
     * @param d1
     * @param d2
     * @param precision
     * @return
     */
    public static String moneyMultiplyNewNum(String d1, String d2, int precision) {
        if (TextUtils.isEmpty(d1) || TextUtils.isEmpty(d2)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
        BigDecimal bigDecimal1 = bigDecimalMultiply.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
        return bigDecimal1.stripTrailingZeros().toPlainString();
    }


    /**
     * 金额计算 除法
     *
     * @param d1
     * @param d2
     * @return BigDecimal.ROUND_HALF_UP  四舍五入
     * BigDecimal.ROUND_UP 进1法
     */
    public static String moneyDivide(String d1, String d2) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_UP);
        return bigDecimalDivide.toString();
    }


    public static String moneyMultiplyNew(String d1, String d2, int num) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
        BigDecimal bigDecimal1 = bigDecimalMultiply.setScale(num, BigDecimal.ROUND_HALF_UP);
        return bigDecimal1.toString();
    }


    public static String moneyDivideNew(String d1, String d2, int num) {
        BigDecimal bigDecimal = new BigDecimal(d1);
        BigDecimal bigDecimal2 = new BigDecimal(d2);
        BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2, num, BigDecimal.ROUND_HALF_UP);
        return bigDecimalDivide.toString();
    }


    /**
     * 验证是否符合私钥规则
     * 规则：数字字母组合  64位或66位
     *
     * @param str
     * @return
     */
    public static boolean isPrivate(String str) {
        return str.matches("^(?![0-9]+$)(?![a-z]+$)[0-9a-z]{64,66}$");
    }

    /**
     * 验证字符串是否是字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        return str.matches("[a-z]+");
    }


    /**
     * BigDecimal比较大小
     * value ：
     * -1 str1小于str2
     * 0  str1等于str2
     * 1  str1大于str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int BigDecimalComparison(String str1, String str2) {
        BigDecimal bigDecimal1 = new BigDecimal(str1);
        BigDecimal bigDecimal2 = new BigDecimal(str2);
        int value = bigDecimal1.compareTo(bigDecimal2);
        return value;
    }


    /**
     * 获取手机设备号
     *
     * @param mContext
     * @return
     */
    public static String getTelePhoneDeviceId(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }


    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        return SharedPreferencesUtils.getInstance().getShared("userId");
    }


    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        return SharedPreferencesUtils.getInstance().getShared("userName");
    }
}
