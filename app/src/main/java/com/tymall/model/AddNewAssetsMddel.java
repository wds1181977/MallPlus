package com.tymall.model;

import java.io.Serializable;

/**
 * Created by zoujiamin on 2018/12/25.
 * 添加新资产数据model
 */

public class AddNewAssetsMddel implements Serializable{


    /**
     * id : 191822198952378368
     * tokenName : ZG Token Test
     * tokenSymbol : ZGT
     * contractAddress : 0xeDd586F6fa42E8404D674bD3FBC72A4Fe734080E
     * imgUrl :
     * decimal : 8
     * ifSelected : 0
     */

    private String id;
    private String tokenName;
    private String tokenSymbol;
    private String contractAddress;
    private String imgUrl;
    private String decimal;

    /**
     * 是否选中 0:未选择 1:已选择
     */
    private String ifSelected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getIfSelected() {
        return ifSelected;
    }

    public void setIfSelected(String ifSelected) {
        this.ifSelected = ifSelected;
    }
}
