package com.tymall.model;


import java.io.Serializable;

/**
 * 兑换记录model
 */
public class ExtendModel implements Serializable{

    /**
     * id : 150
     * uid : 27
     * createtime : 1543549646
     * ordernum : 15435496462764
     * type1 : 2
     * num : 0.1000
     * type2 : 1
     * proportion : 1:82.40901
     * fee : 0.70871000
     * done_num : 7.53218000
     * title1 : ETH
     * title2 : DBM
     */

    private String id;
    private String uid;
    private String createtime;
    private String ordernum;
    private String logo1;

    /**
     * 1 DBM兑换ETH
     * 2 ETH兑换DBM
     */


    private String type1;
    private String num;
    private String type2;
    private String proportion;
    private String fee;
    private String done_num;
    private String title1;
    private String title2;


    public String getLogo1() {
        return logo1;
    }

    public void setLogo1(String logo1) {
        this.logo1 = logo1;
    }

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDone_num() {
        return done_num;
    }

    public void setDone_num(String done_num) {
        this.done_num = done_num;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    @Override
    public String toString() {
        return "ExtendModel{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", createtime='" + createtime + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", type1='" + type1 + '\'' +
                ", num='" + num + '\'' +
                ", type2='" + type2 + '\'' +
                ", proportion='" + proportion + '\'' +
                ", fee='" + fee + '\'' +
                ", done_num='" + done_num + '\'' +
                ", title1='" + title1 + '\'' +
                ", title2='" + title2 + '\'' +
                '}';
    }
}
