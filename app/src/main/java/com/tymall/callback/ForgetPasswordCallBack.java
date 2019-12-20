package com.tymall.callback;

import java.util.List;

/**
 * Created by zoujiamin on 2019/1/3.
 */

public interface ForgetPasswordCallBack {
    void onMnCallBack(String password, List<String> mnemonic, String passwordTip, String name);
    void onPkCallBack(String password, String privateKey, String passwordTip, String name);
}
