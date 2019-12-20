package com.tymall.ui.event;


import java.io.Serializable;
import java.util.List;

public class EosEvent implements Serializable {

    public static final int STATUS_IMPORT_SUCCESS= 101;
    public static final int STATUS_IMPORT_FAIL= 102;
    public static final int STATUS_REG_SUCCESS= 103;
    public static final int STATUS_TX_SUCCESS= 104;
    public static final int STATUS_CHECK_ACCOUNT_NO_EXIT= 105;
    public static final int STATUS_CHECK_ACCOUNT_EXIT= 106;
    public static final int STATUS_TX_CHECK_ACCOUNT= 109;
    public static final int STATUS_TX_IMPORT_EOS_DIALOG= 110;

    public static final int STATUS_CONTACTS_OK= 107;
    public static final int STATUS_IS_LOGIN_SUCCESS= 203;
    public static final int H5_URL= 400;


    private String account;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String mode;
    private int status;
    private int state;
    private String  msg;

    public int getFromCode() {
        return fromCode;
    }

    public void setFromCode(int fromCode) {
        this.fromCode = fromCode;
    }

    private int   fromCode;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getStatus() {
        return status;
    }

    public void setState(int status) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String amount) {
        this.msg = msg;
    }


    public EosEvent(String m,int from) {
        this.mode = m;
        this.fromCode = from;

    }
    public EosEvent(int status) {
        this.status = status;

    }




    public EosEvent(int status, List<String> list) {
        this.status = status;
        this.list = list;


    }


    public EosEvent(int tx_from, int state, String msg) {
        this.status = tx_from;
        this.state = state;
        this. msg = msg;
    }


}
