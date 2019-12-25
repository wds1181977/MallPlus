package com.tymall.model;



import java.io.Serializable;
import java.util.List;

public class GoodsListNewModel implements Serializable {


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
    private List<RecordsBean> records;

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

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean  implements  Serializable{
        /**
         * id : 720
         * memberId : null
         * brandId : 1001000
         * productCategoryId : 1008009
         * feightTemplateId : null
         * productAttributeCategoryId : 10
         * name : 日式色织水洗棉条纹四件套
         * pic : http://yanxuan.nosdn.127.net/3d0045e8f43439c7004fae052b2162ed.png
         * type : null
         * isPaiMai : null
         * expireTime : null
         * productSn : 1115028
         * deleteStatus : null
         * publishStatus : 1
         * newStatus : 1
         * recommandStatus : null
         * verifyStatus : 1
         * sort : 5
         * sale : 1008018
         * price : 319
         * promotionPrice : 0
         * giftGrowth : null
         * giftPoint : null
         * usePointLimit : null
         * subTitle : null
         * description : null
         * originalPrice : 299
         * stock : 1115022
         * lowStock : 0
         * unit : null
         * weight : null
         * previewStatus : null
         * serviceIds : null
         * keywords : null
         * note : null
         * albumPics : null
         * detailTitle : null
         * detailDesc : null
         * detailHtml : null
         * detailMobileHtml : null
         * promotionStartTime : null
         * promotionEndTime : null
         * promotionPerLimit : null
         * promotionType : null
         * brandName : null
         * transfee : null
         * productCategoryName : null
         * supplyId : null
         * areaId : null
         * createTime : 1568863092000
         * schoolId : null
         * hit : null
         * keyword : null
         * areaName : null
         * schoolName : null
         * storeName : null
         * qsType : 0
         * timeSecound : null
         */

        private int id;
        private String memberId;
        private int brandId;
        private int productCategoryId;
        private String feightTemplateId;
        private int productAttributeCategoryId;
        private String name;
        private String pic;
        private String type;
        private String isPaiMai;
        private String expireTime;
        private String productSn;
        private String deleteStatus;
        private int publishStatus;
        private int newStatus;
        private String recommandStatus;
        private int verifyStatus;
        private int sort;
        private int sale;
        private int price;
        private int promotionPrice;
        private String giftGrowth;
        private String giftPoint;
        private String usePointLimit;
        private String subTitle;
        private String description;
        private String originalPrice;
        private int stock;
        private int lowStock;
        private String unit;
        private String weight;
        private String previewStatus;
        private String serviceIds;
        private String keywords;
        private String note;
        private String albumPics;
        private String detailTitle;
        private String detailDesc;
        private String detailHtml;
        private String detailMobileHtml;
        private String promotionStartTime;
        private String promotionEndTime;
        private String promotionPerLimit;
        private String promotionType;
        private String brandName;
        private String transfee;
        private String productCategoryName;
        private String supplyId;
        private String areaId;
        private long createTime;
        private String schoolId;
        private String hit;
        private String keyword;
        private String areaName;
        private String schoolName;
        private String storeName;
        private int qsType;
        private String timeSecound;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(int productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getFeightTemplateId() {
            return feightTemplateId;
        }

        public void setFeightTemplateId(String feightTemplateId) {
            this.feightTemplateId = feightTemplateId;
        }

        public int getProductAttributeCategoryId() {
            return productAttributeCategoryId;
        }

        public void setProductAttributeCategoryId(int productAttributeCategoryId) {
            this.productAttributeCategoryId = productAttributeCategoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsPaiMai() {
            return isPaiMai;
        }

        public void setIsPaiMai(String isPaiMai) {
            this.isPaiMai = isPaiMai;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getProductSn() {
            return productSn;
        }

        public void setProductSn(String productSn) {
            this.productSn = productSn;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
        }

        public int getNewStatus() {
            return newStatus;
        }

        public void setNewStatus(int newStatus) {
            this.newStatus = newStatus;
        }

        public String getRecommandStatus() {
            return recommandStatus;
        }

        public void setRecommandStatus(String recommandStatus) {
            this.recommandStatus = recommandStatus;
        }

        public int getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(int verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(int promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public String getGiftGrowth() {
            return giftGrowth;
        }

        public void setGiftGrowth(String giftGrowth) {
            this.giftGrowth = giftGrowth;
        }

        public String getGiftPoint() {
            return giftPoint;
        }

        public void setGiftPoint(String giftPoint) {
            this.giftPoint = giftPoint;
        }

        public String getUsePointLimit() {
            return usePointLimit;
        }

        public void setUsePointLimit(String usePointLimit) {
            this.usePointLimit = usePointLimit;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getLowStock() {
            return lowStock;
        }

        public void setLowStock(int lowStock) {
            this.lowStock = lowStock;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPreviewStatus() {
            return previewStatus;
        }

        public void setPreviewStatus(String previewStatus) {
            this.previewStatus = previewStatus;
        }

        public String getServiceIds() {
            return serviceIds;
        }

        public void setServiceIds(String serviceIds) {
            this.serviceIds = serviceIds;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getAlbumPics() {
            return albumPics;
        }

        public void setAlbumPics(String albumPics) {
            this.albumPics = albumPics;
        }

        public String getDetailTitle() {
            return detailTitle;
        }

        public void setDetailTitle(String detailTitle) {
            this.detailTitle = detailTitle;
        }

        public String getDetailDesc() {
            return detailDesc;
        }

        public void setDetailDesc(String detailDesc) {
            this.detailDesc = detailDesc;
        }

        public String getDetailHtml() {
            return detailHtml;
        }

        public void setDetailHtml(String detailHtml) {
            this.detailHtml = detailHtml;
        }

        public String getDetailMobileHtml() {
            return detailMobileHtml;
        }

        public void setDetailMobileHtml(String detailMobileHtml) {
            this.detailMobileHtml = detailMobileHtml;
        }

        public String getPromotionStartTime() {
            return promotionStartTime;
        }

        public void setPromotionStartTime(String promotionStartTime) {
            this.promotionStartTime = promotionStartTime;
        }

        public String getPromotionEndTime() {
            return promotionEndTime;
        }

        public void setPromotionEndTime(String promotionEndTime) {
            this.promotionEndTime = promotionEndTime;
        }

        public String getPromotionPerLimit() {
            return promotionPerLimit;
        }

        public void setPromotionPerLimit(String promotionPerLimit) {
            this.promotionPerLimit = promotionPerLimit;
        }

        public String getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(String promotionType) {
            this.promotionType = promotionType;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getTransfee() {
            return transfee;
        }

        public void setTransfee(String transfee) {
            this.transfee = transfee;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

        public String getSupplyId() {
            return supplyId;
        }

        public void setSupplyId(String supplyId) {
            this.supplyId = supplyId;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getHit() {
            return hit;
        }

        public void setHit(String hit) {
            this.hit = hit;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public int getQsType() {
            return qsType;
        }

        public void setQsType(int qsType) {
            this.qsType = qsType;
        }

        public String getTimeSecound() {
            return timeSecound;
        }

        public void setTimeSecound(String timeSecound) {
            this.timeSecound = timeSecound;
        }
    }
}
