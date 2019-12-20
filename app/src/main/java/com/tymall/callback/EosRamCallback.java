package com.tymall.callback;



public interface EosRamCallback {
    void onBuyCallBack(String amount,String from,String to,String ramPrice);
    void onSellCallBack(String ramKb, String from, String to,String eosAmount);

}
