package com.tymall.model;



import java.io.Serializable;

public class BannerModel  implements Serializable {


    /**
     * id : 2
     * status : 1
     * titile :
     * activity_type : 1
     * activity_image : /Uploads/2019-02-20/5c6d40ca553de.jpg
     * activity_url : https://www.baidu.com/
     * language : en
     * create_time : 1541080294
     * update_time : 1541080294
     * **  * 轮播图展示(个人中心/银行活动)默认银行活动  * @param int type_id类型  0银行活动  1个人中心活动 
     *
     */

    private String id;
    private String status;
    private String title;
    private String activity_type;
    private String activity_image;
    private String activity_url;
    private String language;
    private String create_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titile) {
        this.title = titile;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(String activity_image) {
        this.activity_image = activity_image;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public void setActivity_url(String activity_url) {
        this.activity_url = activity_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
