package com.tymall.data;


import com.tymall.utils.SP;


/**
 * Created by wds on 09/17/19.
 */
public class AppDataManager {

    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_COUNTRY_CODE = "USER_COUNTRY_CODE";

    public static final String DBM = "1";
    public static final String DBGAS = "11";


    private static class SingletonHolder {

        private static final AppDataManager INSTANCE = new AppDataManager();

    }

    private AppDataManager() {
    }

    public static final AppDataManager getInstance() {

        return SingletonHolder.INSTANCE;


    }



    public void setPhone(String phone) {
        SP.set(USER_PHONE, phone);
    }

    public String getPhone() {
        return SP.get(USER_PHONE, "");
    }


    public void setCountryCode(String countryCode) {
        SP.set(USER_COUNTRY_CODE, countryCode);
    }

    public String geCountryCode() {
        return SP.get(USER_COUNTRY_CODE, "");
    }


    public void cleanUserPhone(){
        SP.set(USER_PHONE, "");
        SP.set(USER_COUNTRY_CODE, "");
    }


    public void setUserPhone(String phone,String countryCode){
        cleanUserPhone();
        setPhone(phone);
        setCountryCode(countryCode);
    }



}
