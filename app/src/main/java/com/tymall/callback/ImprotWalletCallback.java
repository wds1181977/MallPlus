package com.tymall.callback;

import java.util.List;

public interface ImprotWalletCallback {
    void onMnCallBack(String password, List<String> mnemonic, String passwordTip, String name);
    void onKsCallBack(String password, String keystore, String name);
    void onPkCallBack(String password, String privateKey, String passwordTip, String name);
    void onAgreeCallBack();
}
