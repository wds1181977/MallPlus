package com.tymall.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tymall.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 状态栏设置工具类
 * 说明：
 * 1、android 4.4 提出沉浸模式Immersive Mode。它呈现的形式是全屏状态, 隐藏状态栏与导航栏。
 * 让应用程序内容可以在最大显示范围呈现，增加大屏体验。当需要查看通知时只需要从顶部向下滑动
 * 就能呼出通知栏，也就是全屏拓展。
 * <p>
 * 4.4 还提出透明状态栏Translucent system UI styling，即半透明化的系统UI风格。这个“半透明化”
 * 包括了状态栏和通知栏，当开发者让应用支持这个新特性的时候，状态栏和导航栏可以单独/同时变为
 * 渐变的半透明样式。状态栏也会根据系统的主题是深色还是浅色有一点不同，这样并不是很好看。
 * <p>
 * 2、android 5.0 增加了设置状态栏颜色的api。注意android 4.4设置状态栏颜色是通过透明，即把状态
 * 栏设置为透明，然后在添加一个和状态栏高度相等的View(View的背景色为状态栏颜色)。而5.0是直接通
 * 过api更改。
 * <p>
 * 3、android 6.0 可以更改状态图标和文字的颜色
 * <p>
 * 4、MIUI6+, Flyme4+ 可以更改状态图标和文字的颜色
 * <p>
 * Created by wangheng on 2018/8/19.
 */

public class StatusBarUtils {

    /**
     * 设置状态栏颜色
     * android 4.4以下不支持，使用默认黑色背景状态栏。android 6.0以下（除去MIUI,FLYME）不支持浅色背景（如白色），浅色背景
     * 时默认设置状态栏颜色为colorPrimaryDark
     *
     * @param activity    设置状态栏的activity
     * @param statusColor 状态栏颜色
     * @param isLightMode 是否浅色背景
     */
    public static void setStatusBarColor(Activity activity, int statusColor, boolean isLightMode) {
        /**
         *  如果是浅色主题将状态栏图标和文字内容改为黑色样式,实现浅色主题调背景的状态栏效果。
         *  适用于4.4以上版本MIUIV、Flyme和6.0（API 23）以上版本Android。其他android版本避免
         *  使用白色背景的状态栏，否则看不见状态栏上的图标和文字。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isLightMode) {
                int mode = setStatusBarLightMode(activity);
                // 0代表浅色主题没有设置成功
                if (mode == 0) {
                    statusColor = activity.getResources().getColor(R.color.colorPrimary);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 使用setStatusBarColor的前提条件 取消FLAG_TRANSLUCENT_STATUS
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * （Android4.4）API 19没有提供单独设置“状态栏”颜色的方法，如果要设置，就需要把View的最上端设置出一定高度的
             * 颜色（或者图案），具体高度只要大于等于“状态栏”高度即可，那么已经“透明”了的“状态栏”自然而然就会变成这
             * 个颜色（或者图案）。
             */
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, lp);
        }
    }

    /**
     * 设置状态栏黑色字体图标
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI
     * 2:Flyme
     * 3:android6.0
     * 0：为没有设置浅色主题成功
     */
    public static int setStatusBarLightMode(Activity activity) {
        int result = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity, true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }

        return result;
    }

    public static int setStatusBarDarkMode(Activity activity) {
        int result = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // TODO: 2018/8/30  这是小米的设置白色字体方法，验证是否兼容android 6.0 fiyme
            MIUISetStatusBarDarktMode(activity);
//            if (MIUISetStatusBarDarktMode(activity)) {
//                result = 1;
//            } else if (FlymeSetStatusBarLightMode(activity, false)) {
//                result = 2;
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//                result = 3;
//            }
        }

        return result;
    }

    /**
     * 带状态栏的全屏
     * 内容可伸展到状态栏,类似于抖音效果
     *
     * @param activity
     */
    public static void setFullScreenWithStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 如果用android 21以前的方式会有一个灰色背景在状态栏
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            // false将根rootView直接顶上去，和状态栏的顶部对齐。true时rootview在状态栏下面
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 不带状态栏的全屏
     * splash页面使用
     *
     * @param activity
     */
    public static void setFullScreenNoStatusBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;

        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }

        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     */
    private static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * MIUI的沉浸支持透明白色字体和透明黑色字体
     * https://dev.mi.com/console/doc/detail?pId=1159
     */
    private static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            Class<? extends Window> clazz = activity.getWindow().getClass();
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置MIUI白色字体模式
     *
     * @param activity
     */
    public static void MIUISetStatusBarDarktMode(Activity activity) {
        Window window = activity.getWindow();
        int flag = window.getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.getDecorView().setSystemUiVisibility(flag);
    }

}
