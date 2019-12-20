package com.tymall.model;


import java.io.Serializable;

public class DBMallBannerModel implements Serializable {


    /**
     * id : 8
     * name : vue
     * type : 2
     * pic : http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20191104/banner.jpg
     * startTime : 1572796800000
     * endTime : 1575043200000
     * status : 1
     * clickCount : 0
     * orderCount : 0
     * url : http://www.yjlive.cn:8082/#/
     * note : null
     * sort : 1
     */

    private int id;
    private String name;
    private int type;
    private String pic;
    private long startTime;
    private long endTime;
    private int status;
    private int clickCount;
    private int orderCount;
    private String url;
    private Object note;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
