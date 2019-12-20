package com.tymall.model;



import java.io.Serializable;
import java.util.List;

public class GoodsListModel implements Serializable {


    /**
     * count : 3
     * currentPage : 1
     * goodsList : [{"add_time":1554887966000,"audit_status":0,"cart_num":0,"cashBackDbm":6,"category_id":"202418965923512320","dbm":10,"dbmProp":0.6,"final_price":"15","id":"202515912489454592","integral":7,"integralProp":0.5,"is_delete":0,"is_on_sale":1,"listPics":["/20190410/172112218e6c11.jpg","/20190410/17211739fa67a.jpg","/20190410/1721225135a36c.jpg"],"list_pic_url":"/20190410/172112218e6c11.jpg,/20190410/17211739fa67a.jpg,/20190410/1721225135a36c.jpg","market_price":16.5,"name":"小茗同学","number":"96","primary_pic_url":"http://113.10.156.193:8686/shops_img/images/20190410/17184761816ede.jpg","promotion_desc":"超级好喝","ratationPics":["/20190410/1718557957ca5b.jpg","/20190410/1719011474b80.jpg"],"rotation_pic_url":"/20190410/1718557957ca5b.jpg,/20190410/1719011474b80.jpg","sell_volume":90,"shop_id":"202418708714110976","sid":"202515912489454592","stock_price":1,"version":"28"},{"add_time":1554866230000,"audit_status":0,"cart_num":0,"cashBackDbm":9,"category_id":"202418981837750272","dbm":49,"dbmProp":0.2,"final_price":"70","id":"202493120579651584","integral":56,"integralProp":0.8,"is_delete":0,"is_on_sale":1,"listPics":["/20190410/11162758174917.jpg","/20190410/111632516e7840.jpg","/20190410/11163960058d9a.jpg"],"list_pic_url":"/20190410/11162758174917.jpg,/20190410/111632516e7840.jpg,/20190410/11163960058d9a.jpg","market_price":90,"name":"苹果笔记本","number":"85","primary_pic_url":"http://113.10.156.193:8686/shops_img/images/20190410/111612341c3f0d.jpg","promotion_desc":"实用","ratationPics":["/20190410/111617411de9b5.jpg","/20190410/111622469a40e6.jpg"],"rotation_pic_url":"/20190410/111617411de9b5.jpg,/20190410/111622469a40e6.jpg","sell_volume":63,"shop_id":"202418708714110976","sid":"202493120579651584","stock_price":20,"version":"28"},{"add_time":1554863932000,"audit_status":0,"cart_num":0,"cashBackDbm":4,"category_id":"202418981837750272","dbm":7,"dbmProp":0.6,"final_price":"10","id":"202490710383675392","integral":5,"integralProp":0.5,"is_delete":0,"is_on_sale":1,"listPics":["/20190410/10384550587a98.jpg","/20190410/10384929409fee.jpg"],"list_pic_url":"/20190410/10384550587a98.jpg,/20190410/10384929409fee.jpg","market_price":12,"name":"矿泉水","number":"95","primary_pic_url":"http://113.10.156.193:8686/shops_img/images/20190410/1038334455797.jpg","promotion_desc":"矿泉水12","ratationPics":["/20190410/10383627036772.jpg","/20190410/1038415346eedc.jpg"],"rotation_pic_url":"/20190410/10383627036772.jpg,/20190410/1038415346eedc.jpg","sell_volume":88,"shop_id":"202418708714110976","sid":"202490710383675392","stock_price":2,"version":"17"}]
     * numsPerPage : 3
     * totalPages : 1
     */

    private int count;
    private int currentPage;
    private int numsPerPage;
    private int totalPages;
    private List<GoodsListBean> goodsList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumsPerPage() {
        return numsPerPage;
    }

    public void setNumsPerPage(int numsPerPage) {
        this.numsPerPage = numsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean implements Serializable{
        /**
         * add_time : 1554887966000
         * audit_status : 0
         * cart_num : 0
         * cashBackDbm : 6
         * category_id : 202418965923512320
         * dbm : 10
         * dbmProp : 0.6
         * final_price : 15
         * id : 202515912489454592
         * integral : 7
         * integralProp : 0.5
         * is_delete : 0
         * is_on_sale : 1
         * listPics : ["/20190410/172112218e6c11.jpg","/20190410/17211739fa67a.jpg","/20190410/1721225135a36c.jpg"]
         * list_pic_url : /20190410/172112218e6c11.jpg,/20190410/17211739fa67a.jpg,/20190410/1721225135a36c.jpg
         * market_price : 16.5
         * name : 小茗同学
         * number : 96
         * primary_pic_url : http://113.10.156.193:8686/shops_img/images/20190410/17184761816ede.jpg
         * promotion_desc : 超级好喝
         * ratationPics : ["/20190410/1718557957ca5b.jpg","/20190410/1719011474b80.jpg"]
         * rotation_pic_url : /20190410/1718557957ca5b.jpg,/20190410/1719011474b80.jpg
         * sell_volume : 90
         * shop_id : 202418708714110976
         * sid : 202515912489454592
         * stock_price : 1
         * version : 28
         */

        private long add_time;
        private int audit_status;
        private int cart_num;
        private String cashBackDbm;
        private String category_id;
        private String dbm;
        private double dbmProp;
        private String final_price;
        private String id;
        private String integral;
        private double integralProp;
        private int is_delete;
        private int is_on_sale;
        private String list_pic_url;
        private String market_price;
        private String name;
        private String number;
        private String primary_pic_url;
        private String promotion_desc;
        private String rotation_pic_url;
        private String sell_volume;
        private String shop_id;
        private String sid;
        private int stock_price;
        private String version;
        private List<String> listPics;
        private List<String> ratationPics;
        private String goodDetailsUrl;

        private String currencyName;

        private String currencyPrice;

        public String getCurrencyPrice() {
            return currencyPrice;
        }

        public void setCurrencyPrice(String currencyPrice) {
            this.currencyPrice = currencyPrice;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }






        public String getGoodDetailsUrl() {
            return goodDetailsUrl;
        }

        public void setGoodDetailsUrl(String goodDetailsUrl) {
            this.goodDetailsUrl = goodDetailsUrl;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(int audit_status) {
            this.audit_status = audit_status;
        }

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }

        public String getCashBackDbm() {
            return cashBackDbm;
        }

        public void setCashBackDbm(String cashBackDbm) {
            this.cashBackDbm = cashBackDbm;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getDbm() {
            return dbm;
        }

        public void setDbm(String dbm) {
            this.dbm = dbm;
        }

        public double getDbmProp() {
            return dbmProp;
        }

        public void setDbmProp(double dbmProp) {
            this.dbmProp = dbmProp;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public double getIntegralProp() {
            return integralProp;
        }

        public void setIntegralProp(double integralProp) {
            this.integralProp = integralProp;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public int getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(int is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public String getList_pic_url() {
            return list_pic_url;
        }

        public void setList_pic_url(String list_pic_url) {
            this.list_pic_url = list_pic_url;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrimary_pic_url() {
            return primary_pic_url;
        }

        public void setPrimary_pic_url(String primary_pic_url) {
            this.primary_pic_url = primary_pic_url;
        }

        public String getPromotion_desc() {
            return promotion_desc;
        }

        public void setPromotion_desc(String promotion_desc) {
            this.promotion_desc = promotion_desc;
        }

        public String getRotation_pic_url() {
            return rotation_pic_url;
        }

        public void setRotation_pic_url(String rotation_pic_url) {
            this.rotation_pic_url = rotation_pic_url;
        }

        public String getSell_volume() {
            return sell_volume;
        }

        public void setSell_volume(String sell_volume) {
            this.sell_volume = sell_volume;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public int getStock_price() {
            return stock_price;
        }

        public void setStock_price(int stock_price) {
            this.stock_price = stock_price;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<String> getListPics() {
            return listPics;
        }

        public void setListPics(List<String> listPics) {
            this.listPics = listPics;
        }

        public List<String> getRatationPics() {
            return ratationPics;
        }

        public void setRatationPics(List<String> ratationPics) {
            this.ratationPics = ratationPics;
        }
    }
}
