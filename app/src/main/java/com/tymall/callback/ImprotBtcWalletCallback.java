package com.tymall.callback;

import java.util.List;

public interface ImprotBtcWalletCallback {
    void onMnCallBack(String password, List<String> mnemonic, String passwordTip, String name);
    void onPkCallBack(String password, String privateKey, String passwordTip, String name);
    void onAgreeCallBack();
}
