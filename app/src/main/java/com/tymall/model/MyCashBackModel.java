package com.tymall.model;

import java.io.Serializable;

public class MyCashBackModel implements Serializable {


    /**
     * id : 25
     * uid : 505
     * pid : 1078
     * opt_type : 1
     * type : 1
     * from_uid : null
     * opt_num : 29.00000000
     * create_time : 1556451191
     * remark : 增加待返dbm价值
     */

    private String id;
    private String uid;
    private String pid;
    private String opt_type;
    private String type;
    private Object from_uid;
    private String opt_num;
    private String create_time;
    private String remark;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOpt_type() {
        return opt_type;
    }

    public void setOpt_type(String opt_type) {
        this.opt_type = opt_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(Object from_uid) {
        this.from_uid = from_uid;
    }

    public String getOpt_num() {
        return opt_num;
    }

    public void setOpt_num(String opt_num) {
        this.opt_num = opt_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
