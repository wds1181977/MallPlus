package com.tymall.model;



import java.io.Serializable;
import java.util.List;


public class BoxRankListModel implements Serializable {


    /**
     * boxRankingList : [{"id":"7","phone":"wd****@qq.com","box_name":"至尊宝箱-幸运100%","win_value":"3061","win_value_scale_max":"100.00000000"},{"id":"8","phone":"wd****@qq.com","box_name":"初级宝箱-幸运100%","win_value":"362","win_value_scale_max":"100.00000000"},{"id":"5","phone":"wd****@qq.com","box_name":"中级宝箱-幸运100%","win_value":"190","win_value_scale_max":"100.00000000"},{"id":"15","phone":"xi****@163.com","box_name":"中级宝箱-幸运100%","win_value":"184","win_value_scale_max":"100.00000000"},{"id":"13","phone":"wd****@qq.com","box_name":"初级宝箱-幸运100%","win_value":"138","win_value_scale_max":"100.00000000"},{"id":"10","phone":"wd****@qq.com","box_name":"初级宝箱-幸运100%","win_value":"130","win_value_scale_max":"100.00000000"},{"id":"4","phone":"ab****@qq.com","box_name":"初级宝箱-幸运100%","win_value":"123","win_value_scale_max":"100.00000000"},{"id":"2","phone":"ab****@qq.com","box_name":"初级宝箱-幸运100%","win_value":"59","win_value_scale_max":"100.00000000"},{"id":"6","phone":"wd****@qq.com","box_name":"初级宝箱-幸运100%","win_value":0,"win_value_scale_max":"100.00000000"},{"id":"11","phone":"wd****@qq.com","box_name":"初级宝箱-幸运0%","win_value":0,"win_value_scale_max":"0.00000000"},{"id":"12","phone":"wd****@qq.com","box_name":"初级宝箱-幸运0%","win_value":0,"win_value_scale_max":"0.00000000"},{"id":"3","phone":"ab****@qq.com","box_name":"初级宝箱-幸运100%","win_value":0,"win_value_scale_max":"100.00000000"},{"id":"14","phone":"wd****@qq.com","box_name":"初级宝箱-幸运0%","win_value":0,"win_value_scale_max":"0.00000000"},{"id":"9","phone":"wd****@qq.com","box_name":"初级宝箱-幸运100%","win_value":0,"win_value_scale_max":"100.00000000"}]
     * rankNum : 1
     * boxCount : 14
     */

    private int rankNum;
    private String boxCount;
    private List<BoxRankingListBean> boxRankingList;

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public String getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(String boxCount) {
        this.boxCount = boxCount;
    }

    public List<BoxRankingListBean> getBoxRankingList() {
        return boxRankingList;
    }

    public void setBoxRankingList(List<BoxRankingListBean> boxRankingList) {
        this.boxRankingList = boxRankingList;
    }

    public static class BoxRankingListBean implements Serializable{
        /**
         * id : 7
         * phone : wd****@qq.com
         * box_name : 至尊宝箱-幸运100%
         * win_value : 3061
         * win_value_scale_max : 100.00000000
         */

        private String id;
        private String phone;
        private String box_name;
        private String win_value;
        private String win_value_scale_max;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        private String rank;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBox_name() {
            return box_name;
        }

        public void setBox_name(String box_name) {
            this.box_name = box_name;
        }

        public String getWin_value() {
            return win_value;
        }

        public void setWin_value(String win_value) {
            this.win_value = win_value;
        }

        public String getWin_value_scale_max() {
            return win_value_scale_max;
        }

        public void setWin_value_scale_max(String win_value_scale_max) {
            this.win_value_scale_max = win_value_scale_max;
        }
    }
}
