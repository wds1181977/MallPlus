package com.tymall.model;



import java.io.Serializable;


public class OrderStatusModel implements Serializable {


    /**
     * landStatus : 1
     * landMsg : 588号地圈地成功！
     */

    private String landStatus;
    private String landMsg;

    public String getLandStatus() {
        return landStatus;
    }

    public void setLandStatus(String landStatus) {
        this.landStatus = landStatus;
    }

    public String getLandMsg() {
        return landMsg;
    }

    public void setLandMsg(String landMsg) {
        this.landMsg = landMsg;
    }
}
