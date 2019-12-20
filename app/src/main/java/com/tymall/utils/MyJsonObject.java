package com.tymall.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zoujiamin on 2019/1/2.
 */

public class MyJsonObject extends JSONObject{
    @Override
    public String optString(String name) {

        String result = super.optString(name);
        if(result==null){
            result="";
        }
        return result;
    }

    public MyJsonObject(String json) throws JSONException {
        super(json);
    }
}
