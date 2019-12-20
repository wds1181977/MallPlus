package com.tymall.callback;



public interface EosCpuNetCallback {
    void  onUndelegatebWCallBack(String from, String to, String amount ,String cpuValue,String netValue,String cpuPrice, String netPrice);
    void ondelegatebCallBack( String from, String to,String amount , String cpuValue,String netValue,String cpuPrice, String netPrice);


}
