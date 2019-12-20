package com.tymall.utils;

import android.os.Build;

/**
 * @ProjectName: DBank
 * @Package: com.tymall.utils
 * @ClassName: ModelUtils
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019-09-18 17:05
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019-09-18 17:05
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class ModelUtils {
    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public static boolean isEMUI() {
        String manufacturer = Build.MANUFACTURER;
        if ("HUAWEI".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public static boolean isOPPO() {
        String manufacturer = Build.MANUFACTURER;
        if ("OPPO".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public static boolean isVIVO() {
        String manufacturer = Build.MANUFACTURER;
        if ("vivo".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static int getSystemVersion() {
        return Integer.parseInt(Build.VERSION.RELEASE);
    }
}
