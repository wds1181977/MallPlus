package com.tymall.model;

import java.io.Serializable;

public class BlockRewardModel implements Serializable {

    /**
     * earning_type : 1
     * earning_num : 19.3418
     * create_time : 1564539519
     * area_id : 208427634888205312
     * earning_type_name : DBM
     * date : 2019-07-31
     * remark : USDB新手区
     */

    private String earning_type;
    private String earning_num;
    private String create_time;
    private String area_id;
    private String earning_type_name;
    private String date;
    private String remark;

    public String getEarning_type() {
        return earning_type;
    }

    public void setEarning_type(String earning_type) {
        this.earning_type = earning_type;
    }

    public String getEarning_num() {
        return earning_num;
    }

    public void setEarning_num(String earning_num) {
        this.earning_num = earning_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getEarning_type_name() {
        return earning_type_name;
    }

    public void setEarning_type_name(String earning_type_name) {
        this.earning_type_name = earning_type_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
