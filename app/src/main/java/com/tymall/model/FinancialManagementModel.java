package com.tymall.model;

import java.io.Serializable;

/**
 * Created by zoujiamin on 2019/2/22.
 */

public class FinancialManagementModel implements Serializable {

    /**
     * id : 111
     * activity_image : http://192.168.8.13
     * title :
     */

    private String id;
    private String activity_image;
    private String title;

    /**
     * 是否跳转 跳转类型  0不允许跳   1允许跳转
     */
    private String skip_type;

    public String getSkip_type() {
        return skip_type;
    }

    public void setSkip_type(String skip_type) {
        this.skip_type = skip_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(String activity_image) {
        this.activity_image = activity_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
