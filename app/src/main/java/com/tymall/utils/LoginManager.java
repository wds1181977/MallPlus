package com.tymall.utils;

import android.content.Context;
import android.content.Intent;

import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.app.MyApplication;
import com.tymall.ui.login.LoginActivity;

/**
 * Created by wds on 2019/1/22.
 *
 */

public class LoginManager {


    private static class InstanceHolderClass {
        private static LoginManager sInstance = new LoginManager();
    }

    public static LoginManager getInstance() {
        return InstanceHolderClass.sInstance;
    }


    /**
     * 登录
     *
     * @param
     * @return
     */
    public  synchronized void login(Context context,String from) {

        if (context == null) {
            context = MyApplication.getInstance();
        }
        SharedPreferencesUtils.getInstance().clearUserShared();
        ToastUtils.show(context.getResources().getString(R.string.please_login_to_operate));
        Intent intent = new Intent(context, LoginActivity.class);
        SP.set(Constant.LOGIN_FROM_MARKEKT,false);
        context.startActivity(intent);

    }


    public  synchronized  void loginDBmall(Context context){
        if(!SP.get(Constant.MALL_IS_ALREADY_LOGIN,false)) {
            SharedPreferencesUtils.getInstance().clearUserShared();
            SharedPreferencesUtils.getInstance().clearDBMallShared();
            SP.set(Constant.LOGIN_FROM_MARKEKT,true);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public  synchronized  void loginMon(Context context){
        if(!SP.get(Constant.MON_IS_ALREADY_LOGIN,false)) {
            SharedPreferencesUtils.getInstance().clearUserShared();
            SharedPreferencesUtils.getInstance().clearMonShared();
            SP.set(Constant.LOGIN_FROM_MON,true);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }




}
