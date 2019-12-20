package com.tymall.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * @author: wds
 * @date: 2019/2/28
 * @function: 正则校验
 *  电话号码： ^1(3|4|5|7|8)\\d{9}$
 *  身份证： ^\\d{15}$)|(^\\d{17}([0-9]|X|x)$
 *  验证码  ^\\d{4}$
 *  密码： [A-Za-z0-9]+
 *  银行卡密码验证-6位数字
 */



public class RegexUtil {
    public static boolean checkName(String psw) {
        String regularExpression = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$";
        return Pattern.matches(regularExpression, psw);
    }

    public static boolean checkChinaName(String name) {
        try {
            Pattern pat = Pattern.compile(".*\\d+.*");
            Matcher matcher = pat.matcher(name);

            if (matcher.matches()) {
                return false;
            }

            Pattern p = Pattern.compile("[0-9]*");
            String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Matcher m = p.matcher(name);
            if (m.matches()) {
                return false;
            }

            Pattern pattern = Pattern.compile(limitEx);
            Matcher mLimitEx = pattern.matcher(name);
            if (mLimitEx.find()) {
                return false;
            }
            return  true;
        }catch (Exception e){

        }
        return  true;
    }

    public static boolean checkPsw(String psw) {
        String regularExpression = "[A-Za-z0-9]+";
        return Pattern.matches(regularExpression, psw);
    }

    public static boolean checkModifyBankCardPsw(String psw) {
        String regularExpression = "^\\d{6}$";
        return Pattern.matches(regularExpression, psw);
    }

    public static boolean checkIdentifyingCode(String identifyingCode) {
        String regularExpression = "^\\d{4}$";
        return Pattern.matches(regularExpression, identifyingCode);
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regularExpression = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";
        return Pattern.matches(regularExpression, idCard);
    }




    /**
     * 根据区号判断是否是正确的电话号码
     *
     * @param countryCode :默认国家码
     *                    return ：true 合法  false：不合法
     * @paramphoneNumber :带国家码的电话号码
     */
    public static boolean isPhoneNumberValid(String phoneNumber, String countryCode) {

        System.out.println("isPhoneNumberValid: " + phoneNumber + "/" + countryCode);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: " + e.toString());
        }
        return false;
    }
    /**
     * 验证手机格式
     * 验证成功返回true，验证失败返回false
     *
     * @param mobiles the mobiles
     * @return the boolean
     */
    public static boolean isMobileNO(String mobiles) {
        /*第一位必定为1，第二位必定为3或4或5或6,7或8,9，其他位置的可以为0-9
         */
        Pattern p = Pattern.compile("^((1[3,4,5,6,7,8,9][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 手机号校验
     *
     * @param nullHint 空值的提示信息
     * @param testStrs 需要验证的信息，第一项为手机号
     * @return 手机号校验成功返回TRUE，否则为FALSE
     */
    public static boolean checkPhoneNumAndOtherInfo(@StringRes int nullHint, @NonNull String... testStrs) {
        for (int i = 0; i < testStrs.length; i++) {
            if (TextUtils.isEmpty(testStrs[i])) {
                ToastUtils.show(nullHint);
                return false;
            }
        }
        if (!isPhoneLegal(testStrs[0])) {
            return false;
        }
        return true;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str)
                || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    private static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
//        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        String regExp = "^1(3|4|5|7|8)\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }



    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    private static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验银行卡卡号，参照微信，14位至21位，不做其他校验
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        String regularExpression = "[0-9]{14,21}";
        return Pattern.matches(regularExpression, cardId);
    }

    /**
     * eos账号名
     *
     * @param eosName the eos name
     * @return boolean
     */
    public static boolean isEosName(String eosName) {
        String strPattern = "[1-5a-z]{12}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(eosName);
        return m.matches();
    }

    /**
     * 搜索输入框限制
     *
     * @param eosName the eos name
     * @return boolean
     */
    public static boolean seachName(String eosName) {
        String strPattern = "^[a-z]{1}[1-5a-z]{11}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(eosName);
        return m.matches();
    }

    /**
     * 密码设置成6-20位，并且由字母，数字和符号两种以上组合
     *
     * @param pwd the pwd
     * @return boolean
     */
    public static boolean isPwd(String pwd) {
        String strPattern = "^[\\w\\u4e00-\\u9fa5]{6,12}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 有效数字校验
     *
     * @param value
     * @return
     */
    public static boolean isNumber(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return value.matches(regex);
    }

    public static boolean isIntegerNumber(String str) {// 判断整型
        return str.matches("^\\d+$$");
    }

    public static boolean isDecimalNumber(String str) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
        return str.matches("\\d+\\.\\d+$");
    }

    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param v     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     */
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    public static boolean isPublicKey(String key) {
        return key.startsWith("EOS");
    }
    /**
     * 去除空格换行
     *
     * @param
     * @param
     * @return
     */
    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }


    /**
     * 去除+ -
     *
     * @param
     * @param
     * @return
     */
    public static String replaceAdd(String src) {
        String dest = "";
        try {
            dest = src.replaceAll("\\+", "").replaceAll("\\-", "");
        }catch (Exception e){

        }
        return dest;
    }


}
