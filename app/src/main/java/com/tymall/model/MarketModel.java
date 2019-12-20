package com.tymall.model;

import java.io.Serializable;

/**
 * Created by zoujiamin on 2019/2/23.
 */

public class MarketModel implements Serializable{

    /**
     * id : 1
     * cove : http://192.168.8.13/Uploads/2019-02-22/5c6f67056b7e5.png
     * title : 定期宝
     * purchase_amount : 4000.0000
     * yield_rate : 0.4100
     */

    private String id;
    private String cove;
    private String title;
    private String purchase_amount;
    private String yield_rate;

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

    public String getCove() {
        return cove;
    }

    public void setCove(String cove) {
        this.cove = cove;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(String purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public String getYield_rate() {
        return yield_rate;
    }

    public void setYield_rate(String yield_rate) {
        this.yield_rate = yield_rate;
    }
}
