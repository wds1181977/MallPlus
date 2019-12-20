package com.tymall.model;

import java.io.Serializable;

public class UserModel implements Serializable{


    /**
     * tokenHead : Bearer
     * userInfo : {"id":161,"memberLevelId":4,"memberLevelName":"普通会员","areaId":null,"areaName":null,"schoolName":null,"username":"15801018660","password":"$2a$10$8ERH9qRRMCQNne4mCf9nReYbMAXT1E3I4oGGJV8dfDKDuoEWOyVSS","nickname":null,"phone":"15801018660","status":1,"createTime":1576723559000,"icon":"http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20190830/uniapp.jpeg","gender":null,"birthday":null,"city":null,"job":null,"personalizedSignature":null,"sourceType":null,"integration":10000,"growth":null,"luckeyCount":null,"historyIntegration":null,"avatar":null,"weixinOpenid":null,"invitecode":null,"buyCount":null,"buyMoney":null,"blance":10000,"schoolId":null,"confimpassword":null,"phonecode":null}
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTgwMTAxODY2MCIsImNyZWF0ZWQiOjE1NzY3Mjg3OTE3OTIsImV4cCI6MTY5NjcyODc5MX0.LBaMCX_a795uFkFEHE3dhsy2qQhWt09iD-NMSDBTnUFrw64Uvr3ZYF3_H9DSjNTclaFOCVmIqQPYC43AcLjXXw
     */

    private String tokenHead;
    private UserInfoBean userInfo;
    private String token;

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserInfoBean  implements Serializable {
        /**
         * id : 161
         * memberLevelId : 4
         * memberLevelName : 普通会员
         * areaId : null
         * areaName : null
         * schoolName : null
         * username : 15801018660
         * password : $2a$10$8ERH9qRRMCQNne4mCf9nReYbMAXT1E3I4oGGJV8dfDKDuoEWOyVSS
         * nickname : null
         * phone : 15801018660
         * status : 1
         * createTime : 1576723559000
         * icon : http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20190830/uniapp.jpeg
         * gender : null
         * birthday : null
         * city : null
         * job : null
         * personalizedSignature : null
         * sourceType : null
         * integration : 10000
         * growth : null
         * luckeyCount : null
         * historyIntegration : null
         * avatar : null
         * weixinOpenid : null
         * invitecode : null
         * buyCount : null
         * buyMoney : null
         * blance : 10000
         * schoolId : null
         * confimpassword : null
         * phonecode : null
         */

        private String id;
        private int memberLevelId;
        private String memberLevelName;
        private Object areaId;
        private Object areaName;
        private Object schoolName;
        private String username;
        private String password;
        private Object nickname;
        private String phone;
        private int status;
        private long createTime;
        private String icon;
        private Object gender;
        private Object birthday;
        private Object city;
        private Object job;
        private Object personalizedSignature;
        private Object sourceType;
        private int integration;
        private Object growth;
        private Object luckeyCount;
        private Object historyIntegration;
        private Object avatar;
        private Object weixinOpenid;
        private Object invitecode;
        private Object buyCount;
        private Object buyMoney;
        private int blance;
        private Object schoolId;
        private Object confimpassword;
        private Object phonecode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMemberLevelId() {
            return memberLevelId;
        }

        public void setMemberLevelId(int memberLevelId) {
            this.memberLevelId = memberLevelId;
        }

        public String getMemberLevelName() {
            return memberLevelName;
        }

        public void setMemberLevelName(String memberLevelName) {
            this.memberLevelName = memberLevelName;
        }

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
            this.areaId = areaId;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public Object getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(Object schoolName) {
            this.schoolName = schoolName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getJob() {
            return job;
        }

        public void setJob(Object job) {
            this.job = job;
        }

        public Object getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(Object personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public Object getSourceType() {
            return sourceType;
        }

        public void setSourceType(Object sourceType) {
            this.sourceType = sourceType;
        }

        public int getIntegration() {
            return integration;
        }

        public void setIntegration(int integration) {
            this.integration = integration;
        }

        public Object getGrowth() {
            return growth;
        }

        public void setGrowth(Object growth) {
            this.growth = growth;
        }

        public Object getLuckeyCount() {
            return luckeyCount;
        }

        public void setLuckeyCount(Object luckeyCount) {
            this.luckeyCount = luckeyCount;
        }

        public Object getHistoryIntegration() {
            return historyIntegration;
        }

        public void setHistoryIntegration(Object historyIntegration) {
            this.historyIntegration = historyIntegration;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getWeixinOpenid() {
            return weixinOpenid;
        }

        public void setWeixinOpenid(Object weixinOpenid) {
            this.weixinOpenid = weixinOpenid;
        }

        public Object getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(Object invitecode) {
            this.invitecode = invitecode;
        }

        public Object getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(Object buyCount) {
            this.buyCount = buyCount;
        }

        public Object getBuyMoney() {
            return buyMoney;
        }

        public void setBuyMoney(Object buyMoney) {
            this.buyMoney = buyMoney;
        }

        public int getBlance() {
            return blance;
        }

        public void setBlance(int blance) {
            this.blance = blance;
        }

        public Object getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(Object schoolId) {
            this.schoolId = schoolId;
        }

        public Object getConfimpassword() {
            return confimpassword;
        }

        public void setConfimpassword(Object confimpassword) {
            this.confimpassword = confimpassword;
        }

        public Object getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(Object phonecode) {
            this.phonecode = phonecode;
        }
    }
}
