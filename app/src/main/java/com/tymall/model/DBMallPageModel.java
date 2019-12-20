package com.tymall.model;


import java.io.Serializable;
import java.util.List;





public class DBMallPageModel implements Serializable {


        /**
         * records : [{"id":720,"memberId":null,"brandId":1001000,"productCategoryId":1008009,"feightTemplateId":null,"productAttributeCategoryId":10,"name":"日式色织水洗棉条纹四件套","pic":"http://yanxuan.nosdn.127.net/3d0045e8f43439c7004fae052b2162ed.png","type":null,"isPaiMai":null,"expireTime":null,"productSn":"1115028","deleteStatus":null,"publishStatus":1,"newStatus":1,"recommandStatus":null,"verifyStatus":1,"sort":5,"sale":1008018,"price":319,"promotionPrice":0,"giftGrowth":null,"giftPoint":null,"usePointLimit":null,"subTitle":null,"description":null,"originalPrice":299,"stock":1115022,"lowStock":0,"unit":null,"weight":null,"previewStatus":null,"serviceIds":null,"keywords":null,"note":null,"albumPics":null,"detailTitle":null,"detailDesc":null,"detailHtml":null,"detailMobileHtml":null,"promotionStartTime":null,"promotionEndTime":null,"promotionPerLimit":null,"promotionType":null,"brandName":null,"transfee":null,"productCategoryName":null,"supplyId":null,"areaId":null,"createTime":1568863092000,"schoolId":null,"hit":null,"keyword":null,"areaName":null,"schoolName":null,"storeName":null,"qsType":0,"timeSecound":null}]
         * total : 1157
         * size : 1
         * current : 1
         * pages : 1157
         */

        private int total;
        private int size;
        private int current;
        private int pages;
        private List<DBMallPageModel.RecordsBean> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<DBMallPageModel.RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<DBMallPageModel.RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean  implements  Serializable{

            /**
             * id : 598
             * parentId : 836
             * name : 布艺软装
             * level : 2
             * productCount : null
             * productUnit : null
             * navStatus : null
             * showStatus : null
             * indexStatus : 0
             * sort : null
             * icon : http://yanxuan.nosdn.127.net/8bbcd7de60a678846664af998f57e71c.png
             * keywords : null
             * description : null
             * productAttributeIdList : null
             * childList : null
             */

            private int id;
            private int parentId;
            private String name;
            private int level;
            private Object productCount;
            private Object productUnit;
            private Object navStatus;
            private Object showStatus;
            private int indexStatus;
            private Object sort;
            private String icon;
            private Object keywords;
            private Object description;
            private Object productAttributeIdList;
            private Object childList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public Object getProductCount() {
                return productCount;
            }

            public void setProductCount(Object productCount) {
                this.productCount = productCount;
            }

            public Object getProductUnit() {
                return productUnit;
            }

            public void setProductUnit(Object productUnit) {
                this.productUnit = productUnit;
            }

            public Object getNavStatus() {
                return navStatus;
            }

            public void setNavStatus(Object navStatus) {
                this.navStatus = navStatus;
            }

            public Object getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(Object showStatus) {
                this.showStatus = showStatus;
            }

            public int getIndexStatus() {
                return indexStatus;
            }

            public void setIndexStatus(int indexStatus) {
                this.indexStatus = indexStatus;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Object getKeywords() {
                return keywords;
            }

            public void setKeywords(Object keywords) {
                this.keywords = keywords;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public Object getProductAttributeIdList() {
                return productAttributeIdList;
            }

            public void setProductAttributeIdList(Object productAttributeIdList) {
                this.productAttributeIdList = productAttributeIdList;
            }

            public Object getChildList() {
                return childList;
            }

            public void setChildList(Object childList) {
                this.childList = childList;
            }
        }


}
