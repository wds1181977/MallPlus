package com.tymall.model;


import java.io.Serializable;





public class DBMallPageModel implements Serializable {


    /**
     * id : 21
     * title : 砍价活动
     * icon : http://datong.crmeb.net/public/uploads/attach/2019/03/29/5c9ddedbed782.png
     * summary : /activity/bargain
     * content : /pages/activity/goods_bargain/index
     * sort : 7
     * pcUrl : null
     */

    private int id;
    private String title;
    private String icon;
    private String summary;
    private String content;
    private int sort;
    private Object pcUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(Object pcUrl) {
        this.pcUrl = pcUrl;
    }
}
