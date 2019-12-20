package com.tymall.model;

import java.io.Serializable;
import java.util.List;

public class ShopModel implements Serializable {


    /**
     * cartTotal : {"goodsCount":4,"checkedGoodsCount":4,"goodsAmount":7347,"checkedGoodsAmount":7347}
     * cartList : [{"shop_id":"202418708714110976","shop_name":"韩爱军的店","items":[{"checked":1,"final_price":"209","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=206407500948064256&t=","good_number":"95","goods_id":"206407500948064256","goods_name":"斯伯丁篮球官方正品NBA7号成人耐磨牛皮真皮手感","id":"212019978962749440","market_price":229,"number":1,"priceCurrency":30.28,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190523/161423563fc1be.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"},{"checked":1,"final_price":"7000","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=204046885327752192&t=","good_number":"79","goods_id":"204046885327752192","goods_name":"苹果 iPhone XS Max ","id":"212015283376506880","market_price":8350,"number":1,"priceCurrency":1014.18,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"},{"checked":1,"final_price":"69","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=206407646251337728&t=","good_number":"10","goods_id":"206407646251337728","goods_name":"wilson变形金刚橄榄球3号 大黄蜂幼儿园儿童玩具橄榄球美式足球","id":"211840964892575744","market_price":138,"number":2,"priceCurrency":9.99,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190523/1616422650c04e.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"}]}]
     */

    private CartTotalBean cartTotal;
    private List<CartListBean> cartList;

    public CartTotalBean getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(CartTotalBean cartTotal) {
        this.cartTotal = cartTotal;
    }

    public List<CartListBean> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartListBean> cartList) {
        this.cartList = cartList;
    }

    public static class CartTotalBean {
        /**
         * goodsCount : 4
         * checkedGoodsCount : 4
         * goodsAmount : 7347
         * checkedGoodsAmount : 7347
         */

        private String goodsCount;
        private String checkedGoodsCount;
        private String goodsAmount;
        private String checkedGoodsAmount;

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getCheckedGoodsCount() {
            return checkedGoodsCount;
        }

        public void setCheckedGoodsCount(String checkedGoodsCount) {
            this.checkedGoodsCount = checkedGoodsCount;
        }

        public String getGoodsAmount() {
            return goodsAmount;
        }

        public void setGoodsAmount(String goodsAmount) {
            this.goodsAmount = goodsAmount;
        }

        public String getCheckedGoodsAmount() {
            return checkedGoodsAmount;
        }

        public void setCheckedGoodsAmount(String checkedGoodsAmount) {
            this.checkedGoodsAmount = checkedGoodsAmount;
        }
    }

    public static class CartListBean {
        /**
         * shop_id : 202418708714110976
         * shop_name : 韩爱军的店
         * items : [{"checked":1,"final_price":"209","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=206407500948064256&t=","good_number":"95","goods_id":"206407500948064256","goods_name":"斯伯丁篮球官方正品NBA7号成人耐磨牛皮真皮手感","id":"212019978962749440","market_price":229,"number":1,"priceCurrency":30.28,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190523/161423563fc1be.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"},{"checked":1,"final_price":"7000","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=204046885327752192&t=","good_number":"79","goods_id":"204046885327752192","goods_name":"苹果 iPhone XS Max ","id":"212015283376506880","market_price":8350,"number":1,"priceCurrency":1014.18,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"},{"checked":1,"final_price":"69","goodDetailsUrl":"http://test.shop.d-bank.info/shop/search/product-details.html?productId=206407646251337728&t=","good_number":"10","goods_id":"206407646251337728","goods_name":"wilson变形金刚橄榄球3号 大黄蜂幼儿园儿童玩具橄榄球美式足球","id":"211840964892575744","market_price":138,"number":2,"priceCurrency":9.99,"primary_pic_url":"http://113.10.156.193:8685/shops_img/images/20190523/1616422650c04e.jpg","session_id":"1","shop_id":"202418708714110976","shop_name":"韩爱军的店","user_id":"505"}]
         */

        private String shop_id;
        private String shop_name;
        private boolean ischeck=false;
        private List<ItemsBean> items;


        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * checked : 1
             * final_price : 209
             * goodDetailsUrl : http://test.shop.d-bank.info/shop/search/product-details.html?productId=206407500948064256&t=
             * good_number : 95
             * goods_id : 206407500948064256
             * goods_name : 斯伯丁篮球官方正品NBA7号成人耐磨牛皮真皮手感
             * id : 212019978962749440
             * market_price : 229.0
             * number : 1
             * priceCurrency : 30.28
             * primary_pic_url : http://113.10.156.193:8685/shops_img/images/20190523/161423563fc1be.jpg
             * session_id : 1
             * shop_id : 202418708714110976
             * shop_name : 韩爱军的店
             * user_id : 505
             */

            private int checked;
            private String final_price;
            private String goodDetailsUrl;
            private String good_number;
            private String goods_id;
            private String goods_name;
            private String id;
            private String market_price;
            private int number;
            private String priceCurrency;
            private String primary_pic_url;
            private String session_id;
            private String shop_id;
            private String shop_name;
            private String user_id;
            private String currencyName;
            private boolean ischeck=false;

            public String getCurrencyName() {
                return currencyName;
            }

            public void setCurrencyName(String currencyName) {
                this.currencyName = currencyName;
            }

            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public int getChecked() {
                return checked;
            }

            public void setChecked(int checked) {
                this.checked = checked;
            }

            public String getFinal_price() {
                return final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price;
            }

            public String getGoodDetailsUrl() {
                return goodDetailsUrl;
            }

            public void setGoodDetailsUrl(String goodDetailsUrl) {
                this.goodDetailsUrl = goodDetailsUrl;
            }

            public String getGood_number() {
                return good_number;
            }

            public void setGood_number(String good_number) {
                this.good_number = good_number;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPriceCurrency() {
                return priceCurrency;
            }

            public void setPriceCurrency(String priceCurrency) {
                this.priceCurrency = priceCurrency;
            }

            public String getPrimary_pic_url() {
                return primary_pic_url;
            }

            public void setPrimary_pic_url(String primary_pic_url) {
                this.primary_pic_url = primary_pic_url;
            }

            public String getSession_id() {
                return session_id;
            }

            public void setSession_id(String session_id) {
                this.session_id = session_id;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
