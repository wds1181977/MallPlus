package com.tymall.app;

/**
 * @ProjectName: DBank
 * @Package: com.tymall.app
 * @ClassName: AppConfig
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019-09-18 14:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019-09-18 14:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class AppConfig {


    /**
     * 控制rul环境切换
     * 1：生产环境
     * 2：预生产环境
     * 3:测试环境
     */
    public static final int URL_ENVIRONMENT_VARIABLE = 1;


    /**
     * 是否发版 前总开关
     * true  关闭BugTag ,关闭Log,防止抓包,关闭LeakCanary
     */

    public static boolean isReleaseApk = false;

    /**
     * 是否弹出云节点弹窗
     *
     */
    public static boolean isShowNoticeDialog = true;


}
