package com.tymall.utils;

/**
 * Created by zoujiamin on 2018/12/19.
 */

public class ClickOnUtils {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static final int MIN_CLICK_DELAY_TIME_WU_BAI = 500;
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


    public static boolean isFastClickFiveHundred() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME_WU_BAI) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    /**
     * 自定义间隔时间
     *
     * @param intervalTime
     * @return
     */
    public static boolean isFastClickCustomize(long intervalTime) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= intervalTime) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
