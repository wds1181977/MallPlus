package com.tymall.model;



import java.io.Serializable;


public class BoxListModel implements Serializable {


    /**
     * id : 5
     * uid : 482
     * box_name : 中级宝箱
     * box_level : 1
     * box_value : 10000.00000000
     * box_cycle : 30
     * win_rate : 0.50000000
     * win_value_scale_min : 1.00000000
     * win_value_scale_max : 99.99999999
     * win_value : 190.00000000
     * currency_type : 1
     * addtime : 1555299218
     */

    private String id;
    private String uid;
    private String box_name;
    private String box_level;
    private String box_value;
    private String box_cycle;
    private String win_rate;
    private String win_value_scale_min;
    private String win_value_scale_max;
    private String win_value;
    private String currency_type;
    private String addtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getBox_level() {
        return box_level;
    }

    public void setBox_level(String box_level) {
        this.box_level = box_level;
    }

    public String getBox_value() {
        return box_value;
    }

    public void setBox_value(String box_value) {
        this.box_value = box_value;
    }

    public String getBox_cycle() {
        return box_cycle;
    }

    public void setBox_cycle(String box_cycle) {
        this.box_cycle = box_cycle;
    }

    public String getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(String win_rate) {
        this.win_rate = win_rate;
    }

    public String getWin_value_scale_min() {
        return win_value_scale_min;
    }

    public void setWin_value_scale_min(String win_value_scale_min) {
        this.win_value_scale_min = win_value_scale_min;
    }

    public String getWin_value_scale_max() {
        return win_value_scale_max;
    }

    public void setWin_value_scale_max(String win_value_scale_max) {
        this.win_value_scale_max = win_value_scale_max;
    }

    public String getWin_value() {
        return win_value;
    }

    public void setWin_value(String win_value) {
        this.win_value = win_value;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
