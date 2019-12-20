package com.tymall.model;

import java.io.Serializable;
import java.util.List;

public class OrderListModel implements Serializable {


    /**
     * count : 9
     * currentPage : 1
     * numsPerPage : 9
     * orderList : [{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1564023165,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212094863634352128","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1564023165,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212094863638546432","orderId":"212094863634352128","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1014,"realPayCurrency":1014,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1564023165}],"orderSn":"212094863634352129","orderStatus":0,"orderStatusName":"未付款","payId":"1511","pendingDbmValue":2100,"pendingIntegralValue":1400,"priceCurrency":2028,"priceIntegral":0,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1564023138,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212094835297634304","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1564023138,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212094835300780032","orderId":"212094835297634304","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1014,"realPayCurrency":1014,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1564023138}],"orderSn":"212094835297634305","orderStatus":0,"orderStatusName":"未付款","payId":"1510","pendingDbmValue":2100,"pendingIntegralValue":1400,"priceCurrency":2028,"priceIntegral":0,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1564023056,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212094748835203072","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1564023056,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212094748843591680","orderId":"212094748835203072","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1014,"realPayCurrency":1011,"realPayIntegral":19,"shopName":"韩爱军的店","updateTime":1564023056}],"orderSn":"212094748835203073","orderStatus":0,"orderStatusName":"未付款","payId":"1509","pendingDbmValue":2094.3,"pendingIntegralValue":1396.2,"priceCurrency":1011,"priceIntegral":19,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1564022280,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212093935339456512","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1564022280,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212093935345747968","orderId":"212093935339456512","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1014,"realPayCurrency":1014,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1564022280}],"orderSn":"212093935339456513","orderStatus":0,"orderStatusName":"未付款","payId":"1505","pendingDbmValue":2100,"pendingIntegralValue":1400,"priceCurrency":2028,"priceIntegral":0,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1563949653,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212017780024428544","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1563949653,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212017780031768576","orderId":"212017780024428544","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1012,"realPayCurrency":1010,"realPayIntegral":19,"shopName":"韩爱军的店","updateTime":1563949653}],"orderSn":"212017780024428545","orderStatus":101,"orderStatusName":"已取消","payId":"1504","pendingDbmValue":0,"pendingIntegralValue":0,"priceCurrency":1010,"priceIntegral":19,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1563945305,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212013221196613632","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1563945305,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212013221199759360","orderId":"212013221196613632","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1012,"realPayCurrency":1012,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1563945305}],"orderSn":"212013221196613633","orderStatus":101,"orderStatusName":"已取消","payId":"1503","pendingDbmValue":0,"pendingIntegralValue":0,"priceCurrency":2024,"priceIntegral":0,"remark":"","shopName":"韩爱军的店"},{"actualPrice":7000,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1563944656,"currencyName":"USDB","currencyType":9,"goodsNum":1,"id":"212012540057372672","mobile":"15311946646","orderDetails":[{"categoryId":"204045751833873408","createTime":1563944656,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212012540062615552","orderId":"212012540057372672","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1012,"realPayCurrency":1012,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1563944656}],"orderSn":"212012540057372673","orderStatus":101,"orderStatusName":"已取消","payId":"1502","pendingDbmValue":0,"pendingIntegralValue":0,"priceCurrency":2024,"priceIntegral":0,"remark":"","shopName":"韩爱军的店"},{"actualPrice":288,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":0,"consignee":"jdjf","createTime":1557456732,"currencyName":"DBM","currencyType":1,"goodsNum":1,"id":"205209458987256832","mobile":"15311946646","orderDetails":[{"categoryId":"204045895447890944","createTime":1557456732,"currencyName":"DBM","goodId":"204141062805801984","goodName":"浓缩即食燕窝 70克*3瓶 99%","goodNumber":1,"id":"205209458990402560","orderId":"205209458987256832","picUrl":"http://113.10.156.193:8685/shops_img/images/20190428/154906649b2c6e.png","price":288,"priceCurrency":325,"realPayCurrency":0,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1557456732}],"orderSn":"205209458987256833","orderStatus":0,"orderStatusName":"未付款","payId":"1314","pendingDbmValue":0,"pendingIntegralValue":0,"priceCurrency":325,"priceIntegral":0,"shopName":"韩爱军的店"},{"actualPrice":6264,"address":"北京市 市辖区 东城区 jfjfjfj","canBePaid":1,"consignee":"jdjf","createTime":1557114471,"currencyName":"DBM","currencyType":1,"goodsNum":12,"id":"204850572947115008","mobile":"15311946646","orderDetails":[{"categoryId":"204045895447890944","createTime":1557114471,"currencyName":"DBM","goodId":"204141062805801984","goodName":"浓缩即食燕窝 70克*3瓶 99%","goodNumber":4,"id":"204850572950260736","orderId":"204850572947115008","picUrl":"http://113.10.156.193:8685/shops_img/images/20190428/154906649b2c6e.png","price":288,"priceCurrency":205,"realPayCurrency":0,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1557114471},{"categoryId":"204045895447890944","createTime":1557114471,"currencyName":"DBM","goodId":"204051405693799424","goodName":"海底捞自煮火锅自热小火锅2盒懒人速食网红即食自助麻辣烫荤菜版","goodNumber":2,"id":"204850572950260737","orderId":"204850572947115008","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/160510311547d4.jpg","price":99,"priceCurrency":70,"realPayCurrency":0,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1557114471},{"categoryId":"204045255446383616","createTime":1557114471,"currencyName":"DBM","goodId":"204051016582974464","goodName":"Elta MD氨基酸洁面洗面奶女控油祛痘男补水慕斯乳泡沫深层清洁","goodNumber":3,"id":"204850572951309312","orderId":"204850572947115008","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/155858365bcb49.jpg","price":150,"priceCurrency":106,"realPayCurrency":0,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1557114471},{"categoryId":"204048482613087232","createTime":1557114471,"currencyName":"DBM","goodId":"204049598324165632","goodName":"凤凰自行车33速双碟刹山地车男女变速单车成人26/27.5寸越野赛车","goodNumber":3,"id":"204850572951309313","orderId":"204850572947115008","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/15362380842126.jpg","price":1488,"priceCurrency":1059,"realPayCurrency":0,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1557114471}],"orderSn":"204850572947115009","orderStatus":101,"orderStatusName":"已取消","payId":"1180","pendingDbmValue":0,"pendingIntegralValue":0,"priceCurrency":4455,"priceIntegral":0,"shopName":"韩爱军的店"}]
     * totalPages : 1
     */

//    private String count;
//    private String currentPage;
//    private String numsPerPage;
//    private String totalPages;
    private List<OrderListBean> orderList;

//    public String getCount() {
//        return count;
//    }
//
//    public void setCount(String count) {
//        this.count = count;
//    }
//
//    public String getCurrentPage() {
//        return currentPage;
//    }
//
//    public void setCurrentPage(String currentPage) {
//        this.currentPage = currentPage;
//    }
//
//    public String getNumsPerPage() {
//        return numsPerPage;
//    }
//
//    public void setNumsPerPage(String numsPerPage) {
//        this.numsPerPage = numsPerPage;
//    }
//
//    public String getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(String totalPages) {
//        this.totalPages = totalPages;
//    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean implements Serializable {
        /**
         * actualPrice : 7000.0
         * address : 北京市 市辖区 东城区 jfjfjfj
         * canBePaid : 1
         * consignee : jdjf
         * createTime : 1564023165
         * currencyName : USDB
         * currencyType : 9
         * goodsNum : 1
         * id : 212094863634352128
         * mobile : 15311946646
         * orderDetails : [{"categoryId":"204045751833873408","createTime":1564023165,"currencyName":"USDB","goodId":"204046885327752192","goodName":"苹果 iPhone XS Max ","goodNumber":1,"id":"212094863638546432","orderId":"212094863634352128","picUrl":"http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg","price":7000,"priceCurrency":1014,"realPayCurrency":1014,"realPayIntegral":0,"shopName":"韩爱军的店","updateTime":1564023165}]
         * orderSn : 212094863634352129
         * orderStatus : 0
         * orderStatusName : 未付款
         * payId : 1511
         * pendingDbmValue : 2100.0
         * pendingIntegralValue : 1400.0
         * priceCurrency : 2028.0
         * priceIntegral : 0.0
         * remark :
         * shopName : 韩爱军的店
         */

        private String actualPrice;
        private String address;
        private int canBePaid;
        private String consignee;
        private String createTime;
        private String currencyName;
        private String currencyType;
        private String goodsNum;
        private String id;
        private String mobile;
        private String orderSn;
        private int orderStatus;
        private String orderStatusName;
        private String payId;
        private String pendingDbmValue;
        private String pendingIntegralValue;
        private String priceCurrency;
        private String priceIntegral;
        private String remark;
        private String shopName;
        private List<OrderDetailsBean> orderDetails;


        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCanBePaid() {
            return canBePaid;
        }

        public void setCanBePaid(int canBePaid) {
            this.canBePaid = canBePaid;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getPayId() {
            return payId;
        }

        public void setPayId(String payId) {
            this.payId = payId;
        }

        public String getPendingDbmValue() {
            return pendingDbmValue;
        }

        public void setPendingDbmValue(String pendingDbmValue) {
            this.pendingDbmValue = pendingDbmValue;
        }

        public String getPendingIntegralValue() {
            return pendingIntegralValue;
        }

        public void setPendingIntegralValue(String pendingIntegralValue) {
            this.pendingIntegralValue = pendingIntegralValue;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        public String getPriceIntegral() {
            return priceIntegral;
        }

        public void setPriceIntegral(String priceIntegral) {
            this.priceIntegral = priceIntegral;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public List<OrderDetailsBean> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(List<OrderDetailsBean> orderDetails) {
            this.orderDetails = orderDetails;
        }

        public static class OrderDetailsBean implements Serializable {
            /**
             * categoryId : 204045751833873408
             * createTime : 1564023165
             * currencyName : USDB
             * goodId : 204046885327752192
             * goodName : 苹果 iPhone XS Max
             * goodNumber : 1
             * id : 212094863638546432
             * orderId : 212094863634352128
             * picUrl : http://113.10.156.193:8685/shops_img/images/20190427/1453231786f16e.jpg
             * price : 7000.0
             * priceCurrency : 1014.0
             * realPayCurrency : 1014.0
             * realPayIntegral : 0
             * shopName : 韩爱军的店
             * updateTime : 1564023165
             */

            private String categoryId;
            private String createTime;
            private String currencyName;
            private String goodId;
            private String goodName;
            private String goodNumber;
            private String id;
            private String orderId;
            private String picUrl;
            private String price;
            private String priceCurrency;
            private String realPayCurrency;
            private String realPayIntegral;
            private String shopName;
            private String updateTime;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCurrencyName() {
                return currencyName;
            }

            public void setCurrencyName(String currencyName) {
                this.currencyName = currencyName;
            }

            public String getGoodId() {
                return goodId;
            }

            public void setGoodId(String goodId) {
                this.goodId = goodId;
            }

            public String getGoodName() {
                return goodName;
            }

            public void setGoodName(String goodName) {
                this.goodName = goodName;
            }

            public String getGoodNumber() {
                return goodNumber;
            }

            public void setGoodNumber(String goodNumber) {
                this.goodNumber = goodNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPriceCurrency() {
                return priceCurrency;
            }

            public void setPriceCurrency(String priceCurrency) {
                this.priceCurrency = priceCurrency;
            }

            public String getRealPayCurrency() {
                return realPayCurrency;
            }

            public void setRealPayCurrency(String realPayCurrency) {
                this.realPayCurrency = realPayCurrency;
            }

            public String getRealPayIntegral() {
                return realPayIntegral;
            }

            public void setRealPayIntegral(String realPayIntegral) {
                this.realPayIntegral = realPayIntegral;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
