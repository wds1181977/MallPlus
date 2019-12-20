package com.tymall.app;

import com.tymall.utils.LocalManageUtil;

/**
 * Created by zoujiamin on 2018/11/29.
 * 网络接口请求头
 */

public class UrlConfig {

    /**
     * 获取访问网络开头的baseUrl
     * 1 生产
     * 2 预生产环境
     * 3 测试
     *
     * @return
     */
    public static String getDbankUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://app.d-bank.co/";   //http://temp.d-bank.co/
                break;
            case 2:
                url = "http://test.d-bank.co/";
                break;
            case 3:
                url = "http://apptest.d-bank.co/";
                break;
        }
        return url;
    }


    public static String getDbankUrlETH() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://wallet.d-bank.org/";
                break;
            case 2:
                url = "http://wallet.d-bank.org/";
                break;
            case 3:
                url = "http://apptest.d-bank.org/";
                break;
        }
        return url;
    }


    /**
     * 查询去中心化钱包交易记录baseUrl
     *
     * @return
     */
    public static String getTransactionRecordBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://dwallet.d-bank.org/wallet/";
                break;
            case 2:
                url = "http://dwallet.d-bank.org/wallet/";
                break;
            case 3:
                url = "http://192.168.8.12:8091/b-block/";
                break;
        }
        return url;
    }

    public static String getImageBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://img.d-bank.org/";
                break;
            case 2:
                url = "http://img.d-bank.org/";
                break;
            case 3:
                url = "http://192.168.8.12:8091/";
                break;
        }
        return url;
    }


    /**
     * 查看ETH详细交易记录
     *
     * @return
     */
    public static String GoToEthBrowser() {
        String browseTitle = "";
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                browseTitle = "https://etherscan.io/tx/";
                break;
            case 2:
                browseTitle = "https://etherscan.io/tx/";
                break;
            case 3:
                browseTitle = "https://ropsten.etherscan.io/tx/";
                break;
        }
        return browseTitle;
    }


    /**
     * 查看BTC详细交易记录
     *
     * @return
     */
    public static String GoToBtcBrowser() {
        String browseTitle = "";
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                browseTitle = "https://www.blockchain.com/en/btc/tx/";
                break;
            case 2:
                browseTitle = "https://www.blockchain.com/en/btc/tx/";
                break;
            case 3:
                browseTitle = "https://live.blockcypher.com/btc-testnet/tx/";
                break;
        }
        return browseTitle;
    }


    public static String getBtcUrl() {
        String btcUrl = "";
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                btcUrl = "https://blockchain.info/unspent";
                break;
            case 2:
                btcUrl = "https://blockchain.info/unspent";
                break;
            case 3:
                btcUrl = "https://testnet.blockchain.info/unspent";
                break;
        }
        return btcUrl;
    }


    /**
     * EOS
     * 1 生产
     * 2 预生产环境
     * 3 测试
     *
     * @return
     */
    public static String getEosBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://113.10.156.197:8066/";
                break;
            case 2:
                url = "http://113.10.156.197:8066/";
                break;
            case 3:
                url = "http://192.168.8.12:8066/";
                break;
        }
        return url;
    }


    /**
     * ETH/BTC/USDT等理财折线图、搬砖收益
     *
     * @return
     */
    public static String getMarkeBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://market.d-bank.org/";
                break;
            case 2:
                url = "http://market.d-bank.org/";
                break;
            case 3:
                url = "http://113.10.156.193:8555/";
                break;
        }
        return url;
    }

    /**
     * DBMall Base
     *
     * @return
     */
    public static String getDBMallBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://api.shop.d-bank.org/api/";
                break;
            case 2:
                url = "http://api.shop.d-bank.org/api/";
                break;
            case 3:
                url = "shoptest.d-bank.co";//shoptest.d-bank.co
                break;
        }
        return url;

    }

    /**
     * 商城--订单详情等域名
     * 1 生产
     * 2 预生产环境
     * 3 测试
     *
     * @return
     */
    public static String getMallDomainBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://shop.d-bank.org/";
                break;
            case 2:
                url = "http://shop.d-bank.org/";
                break;
            case 3:
                url = "http://shoph5test.d-bank.co/shop/";
                break;
        }
        return url;
    }

    /**
     * 矿机
     * 1 生产
     * 2 预生产环境
     * 3 测试
     *
     * @return
     */
    public static String getPhoneBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://shop.d-bank.org/";
                break;
            case 2:
                url = "http://shop.d-bank.org/";
                break;
            case 3:
                url = "http://phoneh5test.d-bank.co/";
                break;
        }
        return url;
    }


    /**
     * 地产大富翁baseurl
     * 1 生产
     * 2 预生产环境
     * 3 测试
     *
     * @return
     */
    public static String getMonoployBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://api.d-world.io/";
                break;
            case 2:
                url = "http://api.d-world.io/";
                break;
            case 3:
                url = "http://113.10.156.193:8380/";
                break;
        }
        return url;
    }

    public static String getDbmBpBaseUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                url = "http://www.dbmbp.com/";
                break;
            case 2:
                url = "http://www.dbmbp.com/";
                break;
            case 3:
                url = "http://test.dbmbp.com/browser/";//
                break;
        }
        return url;
    }



    /**
     * 游戏攻略
     *
     * @return
     */
    public static String getGameStrategyUrl() {
        String url = null;
        switch (AppConfig.URL_ENVIRONMENT_VARIABLE) {
            case 1:
                int laguageType1 = LocalManageUtil.getLanguageType(MyApplication.getContext());
                switch (laguageType1) {
                    case Constant.LANGUAGE_CN:
                        url = "http://h5.d-world.io/monoplay/rules-zn.html";
                        break;
                    case Constant.LANGUAGE_KO:
                        url = "http://h5.d-world.io/monoplay/rules-ko.html";
                        break;
                    case Constant.LANGUAGE_EN:
                        url = "http://h5.d-world.io/monoplay/rules-en.html";
                        break;
                }
                break;
            case 2:
                int laguageType2 = LocalManageUtil.getLanguageType(MyApplication.getContext());
                switch (laguageType2) {
                    case Constant.LANGUAGE_CN:
                        url = "http://h5.d-world.io/monoplay/rules-zn.html";
                        break;
                    case Constant.LANGUAGE_KO:
                        url = "http://h5.d-world.io/monoplay/rules-ko.html";
                        break;
                    case Constant.LANGUAGE_EN:
                        url = "http://h5.d-world.io/monoplay/rules-en.html";
                        break;
                }
                break;
            case 3:
                int laguageType3 = LocalManageUtil.getLanguageType(MyApplication.getContext());
                switch (laguageType3) {
                    case Constant.LANGUAGE_CN:
                        url = "http://h5-test.d-bank.co/monoplay/rules-zn.html";
                        break;
                    case Constant.LANGUAGE_KO:
                        url = "http://h5-test.d-bank.co/monoplay/rules-ko.html";
                        break;
                    case Constant.LANGUAGE_EN:
                        url = "http://h5-test.d-bank.co/monoplay/rules-en.html";
                        break;
                }
                break;
        }
        return url;
    }



    /**
     * 空投
     *
     * @return
     */
    public static String getDBGasAirDropUrl() {
        String url = null;
        int laguageType3 = LocalManageUtil.getLanguageType(MyApplication.getContext());
        switch (laguageType3) {
            case Constant.LANGUAGE_CN:
                url = getPhoneBaseUrl() + "dbgasRecord/monthlyAirdrop-cn.html";
                break;
            case Constant.LANGUAGE_KO:
                url = getPhoneBaseUrl() + "dbgasRecord/monthlyAirdrop-ko.html";
                break;
            case Constant.LANGUAGE_EN:
                url = getPhoneBaseUrl() + "dbgasRecord/monthlyAirdrop-en.html";
                break;
        }
        return url;
    }
}
