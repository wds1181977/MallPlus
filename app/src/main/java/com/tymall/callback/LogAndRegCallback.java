package com.tymall.callback;

public interface LogAndRegCallback {
    void onLoginCallBack(String uid, String uname, String token);
    void onRegisterCallBack();
}
