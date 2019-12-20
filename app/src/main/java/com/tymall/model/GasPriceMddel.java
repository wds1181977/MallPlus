package com.tymall.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class GasPriceMddel implements Serializable{


    /**
     * user_limit : {"level_1":{"rate":0.5,"num":"90"},"level_2":{"rate":0.3,"num":"80"},"level_3":{"rate":0.1,"num":"20"},"level_4":{"rate":0.01,"num":"60"}}
     * vip_info : {"vip_level":"1","vip_rate":0}
     * step_day : {"1":7,"2":45,"3":90}
     * over_rate : 0.01
     * rulesList : ["7日内DBM收益手续费","45日内DBM收益手续费","90日内DBM收益手续费","90日外DBM收益手续费","其他币种手续费","普通VIP手续费减免","高级VIP手续费减免","手续费最低10DBM"]
     * rulesValue : ["50%","30%","10%","1%","1%","25%","50%","10DBM"]
     */

    private UserLimitBean user_limit;
    private VipInfoBean vip_info;
    private StepDayBean step_day;
    private String over_rate;
    private List<String> rulesList;
    private List<String> rulesValue;
    private String inputAmount;
    private String fee;
    private String vipValue;
    private String cf;

    public String getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(String inputAmount) {
        this.inputAmount = inputAmount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getVipValue() {
        return vipValue;
    }

    public void setVipValue(String vipValue) {
        this.vipValue = vipValue;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public UserLimitBean getUser_limit() {
        return user_limit;
    }

    public void setUser_limit(UserLimitBean user_limit) {
        this.user_limit = user_limit;
    }

    public VipInfoBean getVip_info() {
        return vip_info;
    }

    public void setVip_info(VipInfoBean vip_info) {
        this.vip_info = vip_info;
    }

    public StepDayBean getStep_day() {
        return step_day;
    }

    public void setStep_day(StepDayBean step_day) {
        this.step_day = step_day;
    }

    public String getOver_rate() {
        return over_rate;
    }

    public void setOver_rate(String over_rate) {
        this.over_rate = over_rate;
    }

    public List<String> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<String> rulesList) {
        this.rulesList = rulesList;
    }

    public List<String> getRulesValue() {
        return rulesValue;
    }

    public void setRulesValue(List<String> rulesValue) {
        this.rulesValue = rulesValue;
    }

    public static class UserLimitBean {
        /**
         * level_1 : {"rate":0.5,"num":"90"}
         * level_2 : {"rate":0.3,"num":"80"}
         * level_3 : {"rate":0.1,"num":"20"}
         * level_4 : {"rate":0.01,"num":"60"}
         */

        private Level1Bean level_1;
        private Level2Bean level_2;
        private Level3Bean level_3;
        private Level4Bean level_4;

        public Level1Bean getLevel_1() {
            return level_1;
        }

        public void setLevel_1(Level1Bean level_1) {
            this.level_1 = level_1;
        }

        public Level2Bean getLevel_2() {
            return level_2;
        }

        public void setLevel_2(Level2Bean level_2) {
            this.level_2 = level_2;
        }

        public Level3Bean getLevel_3() {
            return level_3;
        }

        public void setLevel_3(Level3Bean level_3) {
            this.level_3 = level_3;
        }

        public Level4Bean getLevel_4() {
            return level_4;
        }

        public void setLevel_4(Level4Bean level_4) {
            this.level_4 = level_4;
        }

        public static class Level1Bean implements Serializable{
            /**
             * rate : 0.5
             * num : 0
             */

            private String rate;
            private String num;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class Level2Bean implements Serializable{
            /**
             * rate : 0.3
             * num : 0
             */

            private String rate;
            private String num;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class Level3Bean implements Serializable{
            /**
             * rate : 0.1
             * num : 0
             */

            private String rate;
            private String num;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class Level4Bean implements Serializable{
            /**
             * rate : 0.01
             * num : 0
             */

            private String rate;
            private String num;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }

    public static class VipInfoBean implements Serializable{
        /**
         * vip_level : 1
         * vip_rate : 0
         */

        private String vip_level;
        private String vip_rate;

        public String getVip_level() {
            return vip_level;
        }

        public void setVip_level(String vip_level) {
            this.vip_level = vip_level;
        }

        public String getVip_rate() {
            return vip_rate;
        }

        public void setVip_rate(String vip_rate) {
            this.vip_rate = vip_rate;
        }
    }

    public static class StepDayBean {
        /**
         * 1 : 7
         * 2 : 45
         * 3 : 90
         */

        @SerializedName("1")
        private int _$1;
        @SerializedName("2")
        private int _$2;
        @SerializedName("3")
        private int _$3;

        public int get_$1() {
            return _$1;
        }

        public void set_$1(int _$1) {
            this._$1 = _$1;
        }

        public int get_$2() {
            return _$2;
        }

        public void set_$2(int _$2) {
            this._$2 = _$2;
        }

        public int get_$3() {
            return _$3;
        }

        public void set_$3(int _$3) {
            this._$3 = _$3;
        }
    }
}
