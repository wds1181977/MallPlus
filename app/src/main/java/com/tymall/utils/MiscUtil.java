package com.tymall.utils;

/**
 * Created by zoujiamin on 2018/11/22.
 */

public class MiscUtil {
    private static int FRAGMENT_ID = 10;
    private static int ACTIVITY_ID = 200;


    /**
     * @Title: getActivityId
     * @Description: 获得唯一activity 标识
     * @return
     * @return: int
     */
    synchronized public static int getActivityId() {
        ACTIVITY_ID++;
        return ACTIVITY_ID;
    }

    /**
     * @Title: getFragmentId
     * @Description: 获得fragment 唯一标识
     * @return
     * @return: int
     */
    synchronized public static int getFragmentId() {
        FRAGMENT_ID++;
        return FRAGMENT_ID;
    }
}
