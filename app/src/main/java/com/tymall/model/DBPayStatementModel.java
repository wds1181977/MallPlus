package com.tymall.model;

import java.io.Serializable;

/**
 * DBPay对账单
 */
public class DBPayStatementModel implements Serializable {

    /**
     * id : 27
     * type : 0
     * amount : 66.00000000
     * payment_account : xiaoguoa@163.com
     * create_time : 2019-06-18 11:00
     */

    private String id;

    /**
     * 0:扫描二维码付款 1:预付款充值
     */
    private String type;
    private String amount;
    private String payment_account;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_account() {
        return payment_account;
    }

    public void setPayment_account(String payment_account) {
        this.payment_account = payment_account;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
