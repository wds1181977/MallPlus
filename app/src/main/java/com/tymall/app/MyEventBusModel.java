package com.tymall.app;

import java.io.Serializable;

/**
 * Created by zoujiamin on 2018/11/20.
 * EventBus传递对象标记
 */

public class MyEventBusModel implements Serializable {


    /**
     * 刷新ETH列表页面
     */
    public boolean Refresh_Home_ETH = false;

    /**
     * 刷新BTC列表页面
     */
    public boolean Refresh_Home_BTC = false;


    /**
     * 刷新EOS列表页面
     */
    public boolean Refresh_Home_EOS = false;


    /**
     * ETH或BTC创建成功，刷新页面
     */
    public boolean CREATE_BTC_AND_GO_Refresh_BTC_ETH = false;

    /**
     * 添加钱包地址后，刷新联系人列表
     */
    public boolean Refresh_Contact_List = false;


    /**
     * 刷新币种详情
     */
    public boolean Refresh_Currency_Details = false;


    /**
     * 修改登录密码后、 关闭页面，跳转到登录页面
     */
    public boolean Finish_And_Go_Login = false;

    /**
     * 刷新实名认证
     */

    public boolean Refresh_VER_STATUS = false;


    /**
     * 刷新首页资产列表
     */
    public boolean Refresh_Main_Fragment = false;

    /**
     * 刷新首页钱包名字
     */
    public boolean Refresh_Update_Main_Wallet_Name = false;

    /**
     * 刷新BTC首页钱包名字
     */
    public boolean Refresh_Update_BTC_Wallet_Name = false;


    /**
     * 更新管理钱包页面钱包信息
     */
    public boolean UPDATE_MANAGEMENT_WALLET = false;

    public boolean UPDATE_MANAGEMENT_EOS_WALLET = false;


    public boolean UPDATE_MANAGEMENT_WALLET_AND_NAME = false;


    public boolean UPDATE_MANAGEMENT_BACKUP_WALLET = false;

    public boolean FINISH_CHANAGE_PASSWORD = false;

    public boolean UPDATE_MANAGEMENT_EOS_WALLET_AND_NAME = false;

    public boolean FINISH_CHANAGE_PASSWORD_EOS = false;

    public boolean Refresh_Currency_EOS_Details = false;

    public boolean SUB_EOS_IMPORT_MSG = false;

    public boolean Refresh_DBMALL_TOKEN = false;

    public String amount = "";

    public boolean save_dbm_finish = false;

    public boolean save_dbm_from_finish = false;


    public boolean save_dbm_refresh = false;


    public int Current_Type = 0;

    public int fromType = 0;

    /**
     * 刷新UIR列表页面
     */
    public boolean Refresh_H5_URL = false;


    /**
     * 刷新银行资产页面
     */
    public boolean Refresh_FRAGMENT_BANK = false;


    /**
     * DBPay 支付成功-- 关闭支付页面、返回币种详情
     */
    public boolean DBPay_PayMent_Successful = false;


    /**
     * 刷新我的地产
     */
    public boolean Refresh_My_Property = false;
}
