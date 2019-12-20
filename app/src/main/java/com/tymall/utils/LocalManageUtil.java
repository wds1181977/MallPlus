package com.tymall.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.tymall.app.Constant;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import java.util.Locale;

/**
 * Created by wds on 2018/7/9.
 * 国际化语言设置选项
 */

public class LocalManageUtil {

    private static final String TAG = "LocalManageUtil";

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        return SPUtil.getInstance(context).getSystemCurrentLocal();
    }


    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        switch (SPUtil.getInstance(context).getSelectLanguage()) {
            case 0:
                Locale locale = getSystemLocale(context);
                if (!locale.getLanguage().startsWith("ko") && !locale.getLanguage().startsWith("zh") && !locale.getLanguage().startsWith("en")) {
                    locale = Locale.ENGLISH;
                }
                return locale;
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.ENGLISH;

            case 3:
                return Locale.KOREA;

            default:
                return Locale.ENGLISH;
        }
    }


    public static void saveSelectLanguage(Context context, int select) {
        SPUtil.getInstance(context).saveLanguage(select);
        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        String lauguage = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
            lauguage = locale.getLanguage() + "-" + locale.getCountry();

        }
        resources.updateConfiguration(config, dm);

        if (locale.equals(Locale.SIMPLIFIED_CHINESE) || locale.equals(Locale.TRADITIONAL_CHINESE) || "zh-CN".equals(lauguage)) {
            addPushTag(context,"cn");
            CacheUtils.setLanguage(context, "cn");
        } else if (locale.equals(Locale.KOREA)) {
            addPushTag(context,"ko");
            CacheUtils.setLanguage(context, "ko");
        } else {
            addPushTag(context,"en");
            CacheUtils.setLanguage(context, "en");
        }

    }

    public static int getLanguageType(Context context) {

        int type = Constant.LANGUAGE_EN;
        if ("cn".equals(CacheUtils.getLanguage(context))) {
            type = Constant.LANGUAGE_CN;
        } else if ("ko".equals(CacheUtils.getLanguage(context))) {
            type = Constant.LANGUAGE_KO;
        }else  if("en".equals(CacheUtils.getLanguage(context))){
            type = Constant.LANGUAGE_EN;
        }

        return type;
    }


    public static void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }

        SPUtil.getInstance(context).setSystemCurrentLocal(locale);
    }

    public static void onConfigurationChanged(Context context) {
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }


    private static  void addPushTag (Context context ,String tag){
        if(TextUtils.isEmpty(tag)){
            return;
        }
        try {
            PushAgent mPushAgent = PushAgent.getInstance(context);
            String oldtag = SP.get(Constant.PUSH_CACHE_TAG,"");
            if (!TextUtils.isEmpty(oldtag)) {
                mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
                    @Override
                    public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                        if(isSuccess){
                            mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {

                                @Override
                                public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                                    if (isSuccess) {
                                        SP.set(Constant.PUSH_CACHE_TAG, tag);
                                    }
                                }
                            }, tag);
                        }
                    }
                }, oldtag);
            }else {
                mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {

                    @Override
                    public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                        if (isSuccess) {
                            SP.set(Constant.PUSH_CACHE_TAG, tag);
                        }
                    }
                }, tag);
            }
        }catch (Exception e){

        }

    }
}