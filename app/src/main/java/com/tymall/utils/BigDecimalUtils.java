package com.tymall.utils;


import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 价格运算
 * 说明：
 * created by mufaith at 2018/6/28
 */

public class BigDecimalUtils {


    /**
     * 提供精确的加法运算
     *
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    public static String add3(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_DOWN).toString();
    }

    public static String add6(String v1, String v2, int scale) {
        if(TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2)){return  null;}
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }
    public static String add3(int scale, String... v) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        if (v.length < 2){
            throw new IllegalArgumentException(
                    "至少2个参数");
        }
        BigDecimal result = new BigDecimal(v[0].trim());
        for (int i = 1; i < v.length; i++){
            result = result.add(new BigDecimal(v[i].trim()));
        }
        return result.setScale(scale, BigDecimal.ROUND_DOWN).toString();
    }

    public static String add4(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.add(b2).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toString();
    }

    public static String add4(String... v) {

        if (v.length < 2){
            throw new IllegalArgumentException(
                    "至少2个参数");
        }
        BigDecimal result = new BigDecimal(v[0].trim());
        for (int i = 1; i < v.length; i++){
            result = result.add(new BigDecimal(v[i].trim()));
        }
        return result.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toString();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }


    public static String sub2(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String sub3(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        if (compare(v2,v1) || v1 == null || v2 == null)
            return BigDecimalUtils.round("0",scale);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public static String sub4(String v1, String v2) {

        if (compare(v2,v1) || v1 == null || v2 == null)
            return BigDecimalUtils.round("0",2);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }

    public static String sub5(String v1, String v2, int scale) {

        if (compare(v2,v1) || TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return BigDecimalUtils.round("0",2);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }


    public static String sub8(String amount, int offest) {

        return new BigDecimal(amount).setScale(offest, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String mul2(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String mul3(String v1, String v2, int scale) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return round("0",scale);
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public static String mul4(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return round("0",2);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }
    public static String mul5(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return round("0",2);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.multiply(b2).stripTrailingZeros().toPlainString();
    }

    public static String mul6(String v1, String v2, int scale) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2))
            return round("0",2);
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        if (TextUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();

    }
    public static String div2(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();

    }

    public static String div3(String v1, String v2, int scale) {
        if (v1 == null || v2 == null || "0".equals(v2))
            return round("0",scale);
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).toPlainString();

    }

    public static String div4(String v1, String v2) {
        if (v1 == null || v2 == null || "0".equals(v2))
            return round("0",2);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.divide(b2, 2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

    }


    public static String div5(String v1, String v2,int scale) {
        if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2) || "0".equals(v2))
            return round("0",scale);
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

    }




    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 比较大小
     *
     * @param v1 被比较数
     * @param v2 比较数
     * @return 如果v1 大于v2 则 返回true 否则false
     */
    public static boolean compare(String v1, String v2) {
        if(TextUtils.isEmpty(v1)  || TextUtils.isEmpty(v2))return false;
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        int bj = b1.compareTo(b2);
        boolean res;
        if (bj > 0)
            res = true;
        else
            res = false;
        return res;
    }
    /**
     * 比较大小
     *
     * @param max 被比较数
     * @param v2 比较数
     * @return 如果v1 大于v2 则 返回true 否则false
     */
    public static boolean compareMax(String max, String v2) {
        BigDecimal b1 = new BigDecimal(max);
        BigDecimal b2 = new BigDecimal(v2);
        int bj = b1.compareTo(b2);
        boolean isValid;
        if (bj >= 0)
            isValid = true;
        else
            isValid = false;
        return isValid;
    }


    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round2(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String round3(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String roundDown(String v, int scale) {
        if(v == null )v = "0";
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v.trim());
        return b.setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public static String roundDown2(String v, int scale) {

        if(TextUtils.isEmpty(v)){
            return  v;
        }
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v.trim());
        return b.setScale(scale, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }


    /**
     * 涨跌幅数据返回
     *
     * @param s1
     * @param s2
     * @return
     */
    public static String upOrDownRate(String s1, String s2) {
        String rate;
        rate = mul(div(sub(s1, s2), s2), "100", 2);
        return rate;

    }

    public static boolean isZero(String s1) {
        boolean isZero = false;
        if (TextUtils.isEmpty(s1)) {
            return true;
        }
        BigDecimal d = new BigDecimal(s1);
        if (d.compareTo(BigDecimal.ZERO) == 0) {
            isZero = true;
        }

//        try {
//            float f1 = Float.valueOf(s1);
//            return Math.abs(f1)<1e-6;
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }

        return isZero;
    }



    public static String kickInvalidZero(String s1) {
        if (s1.indexOf(".") > 0) {
            //正则表达
            s1 = s1.replaceAll("0+?$", "");//去掉后面无用的零
            s1 = s1.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s1;

    }

    public static boolean isZeroNew(String s1) {
        boolean isZero=false;
        BigDecimal b = new BigDecimal(s1);
        if ( b.compareTo(BigDecimal.ZERO) == 0) {
            isZero = true;
        }
        return isZero;
    }


}

