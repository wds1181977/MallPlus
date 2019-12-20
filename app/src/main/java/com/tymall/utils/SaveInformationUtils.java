package com.tymall.utils;

import android.content.Context;

/**
 * Created by zoujiamin on 2019/3/8.
 */

public class SaveInformationUtils {

    /**
     * 保存或更新设备
     */
    public static void saveOrUpdateDeciveToken(Context mContext) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("deviceToken", MyApplication.UmengdeviceToken);
//            jsonObject.put("deviceType", "Android");
//            if ("-1".equals(CacheUtils.getWallAddress())) {
//                jsonObject.put("ETH", "");
//            } else {
//                jsonObject.put("ETH", CacheUtils.getWallAddress());
//            }
//
//            if ("-1".equals(CacheUtils.getBtcWallAddress())) {
//                jsonObject.put("BTC", "");
//            } else {
//                jsonObject.put("BTC", CacheUtils.getBtcWallAddress());
//            }
//
//            if (EosWalletUtiles.getInstance().getEosMainAccount() == null) {
//                jsonObject.put("EOS", "");
//            } else {
//                jsonObject.put("EOS", EosWalletUtiles.getInstance().getEosMainAccount());
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("params", jsonObject.toString());
//        CommonParametersUtils.postPackagingMethod(mContext, HttpModel.saveOrUpdateDeciveToken, map, new StringCallback() {
//            @Override
//            public void onSuccess(ApiResponse<String> response) {
//
//            }
//        });
    }
}
