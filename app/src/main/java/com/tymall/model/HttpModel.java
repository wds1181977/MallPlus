package com.tymall.model;

import com.tymall.app.AppConfig;
import com.tymall.app.UrlConfig;

/**
 * 接口实体
 */
public class HttpModel {

    public static String URL = UrlConfig.getDbankUrl();
    public static String URLETH = UrlConfig.getDbankUrlETH();
    public static String markUrl = UrlConfig.getMarkeBaseUrl();
    public static String DBMallUrl = UrlConfig.getDBMallBaseUrl();
    public static String MonoployUrl = UrlConfig.getMonoployBaseUrl();
    //趣投票官网
    public static String VOTE_OFFICIAL_WEBSITE = AppConfig.URL_ENVIRONMENT_VARIABLE == 3 ? "http://www-test.dbmbp.com/" : "http://www.qutoupiao.cc";


    public static String transactionRecordBaseUrl = UrlConfig.getTransactionRecordBaseUrl();
    public static String EOS_BASE_URL = UrlConfig.getEosBaseUrl();
    public static String EOS_Block_Chain_Browser_URL = "https://eosflare.io/tx/";

    public static String Login = URL + "Api/Login/login"; // 登录
    public static String Register = URL + "Api/Register/register"; // 注册
    public static String MemoryWords = URL + "Api/Register/note"; // 获取助记词
    public static String Confirm = URL + "Api/Register/confirm"; // 获取私钥
    public static String PoolTotal = URL + "Api/OilBaby/total"; // 获取矿池总数
    public static String OpenPool = URL + "Api/OilBaby/joins"; // 开启矿池
    public static String QuitPool = URL + "Api/OilBaby/quit"; // 关闭矿池
    public static String OpenHistory = URL + "Api/OilBaby/join_quit_logs"; // 开启记录
    public static String VipRule = URL + "Api/Vip/rule_info"; // VIP 规则公告接口
    public static String VipApply = URL + "Api/Vip/apply_vip"; // 提交vip申请接口
    public static String UnApplyVip = URL + "Api/Vip/revoke_vip"; // 撤销VIP申请
    public static String Article = URL + "Api/Article/questions"; // 文章列表接口
    public static String ExchangeRecord = URL + "Api/Exchange/index"; // 兑换记录
    public static String Extend = URL + "Api/Public/AppDown"; // 推广链接
    public static String BankCoinList = URL + "Api/Money/info"; // 获取币种列表
    public static String BankLastRec = URL + "Api/Money/last_log"; // 获取最新一条交易记录
    public static String GetBankMoney = URL + "Api/Money/bank_num"; // 获取总资产
    public static String PayRecord = URL + "Api/Payment/pay_record"; // 获取交易记录

    public static String Quotation = URL + "Api/Price/price_auth"; // 行情

    public static String AddAddress = URL + "Api/WithdrawAddress/address_add"; // 添加钱包地址
    public static String DelAddress = URL + "Api/WithdrawAddress/del_address"; // 删除钱包地址
    public static String getCoin = URL + "Api/WithdrawAddress/currency_type"; // 添加钱包 获取币列表
    public static String getContacts = URL + "Api/WithdrawAddress/select_address"; // 获取用户添加的币种地址
    public static String AlterPwd = URL + "Api/Info/save_password"; //修改密码
    public static String ForgetPwd = URL + "Api/Login/key_savepass"; // 忘记密码
    public static String ExportKeyStore = URL + "Api/Member/exportKeyStore"; // 导出私钥
    public static String GetETHInfo = URL + "Api/Price/get_eth_info"; // 获取ETH 信息
    public static String GetDBTInfo = URL + "Api/Money/get_dbm_info"; // 首页获取DBT信息
    public static String GetCoinInfo = URL + "Api/Money/one_currency_info"; // 获取一种币的数量，价值，交易记录
    public static String GetWalletAddr = URL + "Api/Money/currency_address"; // 获取中心化钱包地址
    public static String GetGas = URL + "Api/Payment/pay_info"; // 获取中心化矿工费
    public static String GetDBGas = URL + "Api/PaymentForMining/dbgasFeeInfo"; // 获取DBGas比例和最小





    public static String doPay = URL + "Api/Payment/pay"; // 中心化扫码付款转账接口

    public static String Notice = URL + "Api/Article/notices"; // 系统公告

    public static String messages = URL + "Api/Article/messages"; // 消息中心

    public static String messagesNews = URL + "Api/Article/messgaeNews"; // 消息中心外



    public static String ExchangeNum = URL + "Api/Money/exchange_num"; // 币种兑换显示界面->输入金额立刻返回可以兑换成多少
    public static String ExchangeCoin = URL + "Api/Money/dbank_exchange"; // dbank币种兑换信息
    public static String getAllSymbol = URL + "Api/UserDayLimit/getAllSymbol"; //获取所有交易对
    public static String getRate = URL + "Api/UserDayLimit/getRate";  //获取汇率接口
    public static String getExchangeGas = URL + "Api/UserDayLimit/getRate";  //闪兑手续费


    public static String DoExchange = URL + "Api/Money/currency_exchange"; // 币种兑换
    //    public static String ContactUs = URL + "Api/Article/config_info"; // 联系我们
    public static String ContactUs = URL + "Api/Contact/index"; // 联系我们
    public static String ProfitList = URL + "Api/Member/incomeCount"; // 收益清单
    public static String GetContent = URL + "Api/Article/content"; // 文章内容接口
    public static String GetVipNum = URL + "Api/Vip/apply_num"; // 申请vip eoc数量下拉列表接口
    public static String GetVipState = URL + "Api/Vip/is_vip"; // 申请vip状态
    public static String BusinessPartnerNew = URL + "Api/Member/partnersV2"; // 获取商业伙伴
    public static String getOilAccountsByUid = URL + "Api/Member/getOilAccountsByUid"; // 获取商业伙伴详情
//    public static String getVersion = URL + "Api/Article/appinfo"; // 获取最新版本

    public static String ETHTransaction = URLETH + "api/etherscan/getethtransactions.api"; // 获取以太坊交易记录

    public static String mainPrice = URL + "Api/Price/price_auth"; // 获取ETH和DBM行情

    public static String userInfo = URL + "Api/Info/user_info"; // 个人主页数据（vip 签名）

    public static String saveHeadImage = URL + "Api/Info/save_headImage"; //上传头像

    public static String saveSignature = URL + "Api/Info/save_signature"; //修改个性签名

    public static String androidInfo = URL + "Api/Article/android_info"; //版本更新

    public static String checkUpdate = URL + "Api/Article/check_update"; //版本更新检测

    public static String getWithdraw = URL + "Api/Withdraw/get_withdraw"; //提币记录


    public static String userAgreement = URL + "Api/Article/userAgreement"; //用户协议
    public static String registerAgreement = URL + "Api/Article/registerAgreement"; //注册协议
    public static String serviceAgreement = URL + "Api/Article/serviceAgreement"; //服务协议


    public static String getEarnLogs = URL + "Api/Member/getEarnLogs"; //查询智能收益列表接口

    public static String getRecommendEarnLogs = URL + "Api/Member/getRecommendEarnLogs"; //查询链接收益列表接口


    public static String ruleInfo = URL + "/Api/OilBaby/ruleInfo";//查询关闭矿池提示

    public static String selectAllAddress = URL + "/Api/WithdrawAddress/selectAllAddress";//获取全部联系人


    public static String AppDownNew = URL + "Api/Public/AppDownNew";//获取邀请码和推广链接


    public static String saveTradRecordInfo = transactionRecordBaseUrl + "decentraMessage/saveTradRecordInfo"; // 保存交易记录


    public static String getAssetInfo = transactionRecordBaseUrl + "/bankApi/getAssetInfo"; //ETH币种多币余额详情

//    public static String saveWalletExcetionInfo = transactionRecordBaseUrl + "/bankApi/saveWalletExcetionInfo";//异常用户专用


    public static String getMultipleCurrencies = transactionRecordBaseUrl + "/bankApi/getMultipleCurrencies";//查询多币种列表

    public static String changeRelation = transactionRecordBaseUrl + "/bankApi/changeRelation";//更改依赖关系


    public static String findNonceByAddress = transactionRecordBaseUrl + "bankApi/findNonceByAddress"; // 查询支持的币种列表

    public static String sendRawTransaction = transactionRecordBaseUrl + "bankApi/sendRawTransaction"; // ETH转账

    public static String findMessageByAddress = transactionRecordBaseUrl + "decentraMessage/findMessageByAddress"; // 查询ETH交易记录列表

    public static String insertDecentraAddress = transactionRecordBaseUrl + "decentraMessage/insertDecentraAddress"; //  保存ETH钱包地址

    public static String getAssetList = transactionRecordBaseUrl + "btcBankApi/getAssetList"; //首页BTC列表

    public static String getNewAssetList = transactionRecordBaseUrl + "bankApi/getNewAssetList"; //首页ETH列表


    public static String saveBtcAddress = transactionRecordBaseUrl + "btcBankApi/saveBtcAddress"; //保存BTC钱包地址

    public static String findBtcBalanceByAddress = transactionRecordBaseUrl + "btcBankApi/findBtcBalanceByAddress"; //查询btc余额

    public static String findBtcRecordByAddress = transactionRecordBaseUrl + "btcBankApi/findBtcRecordByAddress"; //查询btc交易记录

    public static String btcPushTx = transactionRecordBaseUrl + "btcBankApi/btcPushTx"; //发送BTC签名

    public static String findBtcFee = transactionRecordBaseUrl + "btcBankApi/findBtcFee"; //查询btc的 fasterFee

    public static String findTradByTokenName = transactionRecordBaseUrl + "decentraMessage/findTradByTokenName"; //查询ETH单个币种交易记录

    public static String getAddressList = URL + "Api/ContactsAddress/get_address_list"; // 联系人列表(新)

    public static String addressAdd = URL + "Api/ContactsAddress/address_add"; // 添加联系人(新)

    public static String currencyType = URL + "Api/ContactsAddress/currency_type"; // 获取币种类型(新)

    public static String updateAddress = URL + "Api/ContactsAddress/update_address"; // 修改联系人(新)

    public static String delAddress = URL + "Api/ContactsAddress/del_address"; // 删除联系人(新)

    public static String benefitCurveMine = URL + "Api/OilBaby/benefit_curve_mine"; // 曲线图数据


    public static String getUserBannerData = URL + "Api/Activity/activityRotationChart"; // 轮播图展示(个人中心/银行活动)默认银行活动  * @param int type_id类型  0银行活动  1个人中心活动 
    public static String getBannerForUserCenter = URL + "Api/Activity/getBannerForUserCenter"; // 轮播图展示(个人中心/银行活动)默认银行活动  * @param int type_id类型  0银行活动  1个人中心活动 

    public static String bankHome = URL + "Api/Activity/bankHome"; //银行首页

    public static String bankHomeNew = URL + "Api/Activity/bankHomeV2"; //银行首页（新）

    public static String notice_title = URL + "Api/Article/notice_broadcast"; // 个人中心公告头

    public static String notice_list = URL + "Api/Article/notice_list"; // 个人中心公告列表

    public static String MessageNoticeList = URL + "Api/Message/notice_list"; // 个人中心公告列表

    public static String notice_details_ = URL + "Api/Article/notice_details"; //个人中心公告详情

    public static String joinLogsList = URL + "Api/OilBaby/joinLogsList"; // 矿池(开启状态)的加入记录 

    public static String pressIdQuit = URL + "Api/OilBaby/pressIdQuit"; // 关闭矿池

    public static String showProductsList = URL + "Api/FortuneProducts/showProductsList"; // 理财产品列表

    public static String userProductsInfo = URL + "Api/FortuneProducts/userProductsInfo"; // 银行理财产品

    public static String userHoldProducts = URL + "Api/FortuneProducts/userHoldProducts"; // 用户持有理财产品

    public static String marketPay = URL + "Api/PaymentForShop/payment"; // 商城交易确认

    public static String computeQuitFee = URL + "Api/OilBaby/computeQuitFee";// 计算扣除手续费

    public static String getEosAccountMsgByPubkey = EOS_BASE_URL + "/account/getAccountByKey"; // 通过公钥查询有无账户
    public static String getEosAccountDetails = EOS_BASE_URL + "/account/getAccountInfo"; // 账户信息
    public static String getEosChainInfo = EOS_BASE_URL + "/eos/getChainInfo"; // Eos节点信息
    public static String getEosHomeData = EOS_BASE_URL + "/account/getAccountBalance"; //首页EOS列表
    public static String pushTransaction = EOS_BASE_URL + "/eos/pushTransaction"; // push
    public static String getHistoryTransaction = EOS_BASE_URL + "/eos/getHistoryTransaction"; // 交易记录
    public static String getRamPrice = EOS_BASE_URL + "/eos/getRamPrice"; // 获取RAM价格
    public static String saveCreateAccountInfo = EOS_BASE_URL + "account/saveCreateAccountInfo"; //提交工单
    public static String createAccountShow = EOS_BASE_URL + "/eos/createAccountShow"; //获取创建账户最小资源
    public static String paymentForCreateEos = URL + "Api/PaymentForShop/paymentForCreateEos"; //创建EOS转账

    public static String saveOrUpdateDeciveToken = transactionRecordBaseUrl + "/decentraMessage/saveOrUpdateDeciveToken"; //保存或更新设备

    public static String checkEosAccount = EOS_BASE_URL + "/account/isNewAccount"; //查询是否存在创建钱包时用


    public static String getMoneyInfoByAddress = URL + "Api/Money/getMoneyInfoByAddress"; //扫码获取地址信息


    public static String getInviteFriendsURL = URL + "home/register/invite-friends.html";//个人礼包
    public static String getInviteFriendsURL_EN = URL + "home/register/invite-friends-en.html";//个人礼包英文
    public static String getInviteFriendsURL_KO = URL + "home/register/invite-friends-korean.html";//个人礼包韩文


    public static String getMarketList = markUrl + "market/getMarketList"; //BTC/ETH理财折线图
    public static String getMarketPositionList = markUrl + "market/getMarketPositionList";//搬砖收益

    public static String getBoxMessage = URL + "Api/box/boxMessage"; //宝箱公告

    public static String getMyBoxList = URL + "Api/box/getBoxList";//我的宝箱

    public static String getLuckList = URL + "Api/box/boxRankingList";//好运榜


    public static String openBox = URL + "Api/box/openBoxV3";//开启宝箱

    public static String getSaveRecord = URL + "market/getMarketPositionList";//缴存DBM记录

    public static String getTurnOutDbmRecord = URL + "market/getMarketPositionList";//转出DBM记录

    public static String getBoxRule = URL + "Api/box/getBoxRule"; //宝箱规则

    public static String getOTCURL = "http://www.51otc.io/";//OTC URL


    public static String getDBMallHomeBanner = DBMallUrl + "banner/getHomeBanner";//DBMall Banner

    public static String getDBMallGoodsList = DBMallUrl + "goods/list";//DBMall 商品管理

    public static String getDBMallLogin = DBMallUrl + "auth/login";//DBMall 商城登录返回商城Token

    public static String categoryGoodlist = DBMallUrl + "/goods/categoryGoodlist";//DBMall 首页商品列表


    public static String queryCartCount = DBMallUrl + "cart/queryCartCount";//DBMall 购物车数量


    public static String queryGetCartIndex = DBMallUrl + "cart/index";//DBMall 查询购物车中的数据


    public static String pointsAccount = URL + "Api/Points/pointsAccount";//DBMall 积分账户查询接口

    public static String pointsAccountLogs = URL + "Api/Points/pointsAccountLogs";//DBMall 积分账户操作记录接口

    public static String pointsLogs = URL + "Api/Points/pointsLogs";//DBMall 积分账户积分记录接口


    public static String pointsLinkLogs = URL + "api/Points/pointsLinkLogs";//DBMall 商城返现 链接收益

    public static String getOrderList = DBMallUrl + "order/list";//DBMall 获取订单列表

    public static String cancelOrder = DBMallUrl + "order/cancel";//DBMall 取消订单

    public static String deleteCart = DBMallUrl + "cart/delete";//DBMall 购物车删除选中商品


    public static String addCart = DBMallUrl + "cart/add";//DBMall 购物车商品添加数量

    public static String minusCart = DBMallUrl + "cart/minus";//DBMall 购物车商品减少

    public static String getOrderR2 = DBMallUrl + "order/getR2";//DBMall 获取R2

    public static String confimAcceptOrder = DBMallUrl + "order/confimAccept";//DBMall 确认收货


    public static String judgeGoodsNumber = DBMallUrl + "cart/judgeGoodsNumber";//提交订单、检查库存

    public static String getPhoneURL = UrlConfig.getMallDomainBaseUrl() + "dbphone-presale.html";//老版商城

//    public static String getPhoneURLChineseNew = UrlConfig.getMallDomainBaseUrl() + "miner/miner-zn.html";//区块链手机-中文
//    public static String getPhoneURLEnglishNew = UrlConfig.getMallDomainBaseUrl() + "miner/miner-en.html";//区块链手机--英文
//    public static String getPhoneURLKoNew = UrlConfig.getMallDomainBaseUrl() + "miner/miner-ko.html";//区块链手机--韩文


    public static String getPhoneURLChinese = UrlConfig.getPhoneBaseUrl() + "dbphone-presale-zn.html";//区块链手机-中文
    public static String getPhoneURLEnglish = UrlConfig.getPhoneBaseUrl() + "dbphone-presale-en.html";//区块链手机--英文
    public static String getPhoneURLKo = UrlConfig.getPhoneBaseUrl() + "dbphone-presale-ko.html";//区块链手机--韩文

    public static String getDbmBpURLChinese = UrlConfig.getDbmBpBaseUrl() + "index.html";
    public static String getDbmBpURLEnglish = UrlConfig.getDbmBpBaseUrl() + "index-en.html";
    public static String getDbmBpURLKo = UrlConfig.getDbmBpBaseUrl() + "index-ko.html";


    public static String getDbmBpBoxURLChinese = UrlConfig.getPhoneBaseUrl() + "miner/mobileNode-zn.html";
    public static String getDbmBpBoxURLEnglish = UrlConfig.getPhoneBaseUrl() + "miner/mobileNode-en.html";
    public static String getDbmBpBoxURLKo = UrlConfig.getPhoneBaseUrl() + "miner/mobileNode-ko.html";

    public static String getDbmBpBoxCloudURLChinese = UrlConfig.getPhoneBaseUrl() + "miner/cloudNode-zn.html";
    public static String getDbmBpBoxCloudURLEnglish = UrlConfig.getPhoneBaseUrl() + "miner/cloudNode-en.html";
    public static String getDbmBpBoxCloudURLKo = UrlConfig.getPhoneBaseUrl() + "miner/cloudNode-ko.html";



    public static String getMarketPaySuccess = UrlConfig.getMallDomainBaseUrl() + "orders/successful-transaction.html";//商城支付成功


    public static String getUserPhoneVerification = URL + "api/Verification/getUserPhoneVerification";//获取用户手机绑定信息
    public static String checkSmsCode = URL + "api/Verification/checkSmsCode";// 验证短信验证码 参数：phone 手机号  sms_code 短信验证码
    public static String sendSmsCode = URL + "api/Verification/sendSmsCode";//参数：phone 手机号   ver_code 图文验证码
    public static String getVerificationCode = URL + "api/Verification/getCode";//获取图片验证码（发短信验证码时调用）
    public static String sendSmsCodeForPayment = URL + "api/Verification/sendSmsCodeForPayment";// 转账提币 发验证码



    public static String yuebaoAccount = URL + "api/YuebaoAccount/yuebaoAccount";//用户余额宝信息

    public static String yuebaoAccountLogs = URL + "api/YuebaoAccount/yuebaoAccountLogs";//用户余额宝明细

    public static String joinYuebao = URL + "api/YuebaoAccount/joinYuebao";//加入余额宝


    public static String quitYuebao = URL + "api/YuebaoAccount/quitYuebao";//转出余额宝

    public static String yuebaoAccountEarnLogs = URL + "api/YuebaoAccount/yuebaoAccountEarnLogs";//余额宝收益记录

    public static String yuebaoRegular = URL + "api/YuebaoAccount/yuebaoRegular";//余额宝宝箱记录


    public static String hqbIsShow = URL + "api/Box/hqbIsShow";//是否显示活期宝


    public static String fortuneProductsList = URL + "Api/FortuneProducts/fortuneProductsList";//首页--智能量化和DBM理财

    public static String fortuneProductsListForMarket = URL + "Api/FortuneProducts/fortuneProductsListForMarket";//市场--智能量化和DBM理财

    public static String dappList = URL + "api/Dapps/dappList";//是否显示活期宝

    public static String scanCodeInfo = URL + "/Api/DBPay/scanCodeInfo";//商家二维码信息


    public static String paymentDBPay = URL + "Api/DBPay/payment";//DBPay支付

    public static String dbpayInfo = URL + "Api/DBPay/dbpayInfo";//DBPay商家信息

    public static String addMiningPhone = URL + "api/MiningPhone/addMiningPhone";//绑定挖矿手机  imei 设备号. uid用户 ID

    public static String getMiningPhone = URL + "api/MiningPhone/getMiningPhone";//获取挖矿手机绑定信息   参数uid

    public static String getMonoployZone = MonoployUrl + "/mo/landZone/zoneList";// 大富翁首页选择区


    public static String buyProperty = MonoployUrl + "mo/land/buyLand";//购买地块

    public static String findOrderStatusInfo = MonoployUrl + "mo/landOrder/findOrderStatusInfo"; //查询圈地是否成功API


    public static String zoneRulesList = MonoployUrl + "mo/landRules/zoneRulesList";// 圈地规则

    public static String createBuyLandOrder = MonoployUrl + "mo/landOrder/createBuyLandOrder";//  创建购买地块订单

    public static String updateOrderStatus = MonoployUrl + "mo/landOrder/updateOrderStatus";//  取消或修改订单


    public static String getSlotNotice = MonoployUrl + "/mo/winLog/selectNoticeRecord"; //获取摇号公告


    public static String getMonLogin = MonoployUrl + "mo/userToken/login";//登录到地产大富翁

    public static String getSlotCount = MonoployUrl + "mo/winLog/selectLottery";//获取摇号次数

    public static String startSlot = MonoployUrl + "mo/winLog/lottery";//摇号


    public static String getSlotCode = MonoployUrl + "mo/winLog/getLotteryResult";//获取摇号号码

    public static String getSlotRecordList = MonoployUrl + "mo/winLog/selectLotteryRecord";//查询摇号公示或记录 1:查询摇号记录   2.查询摇号公示


    public static String getMonopolyMapCode = MonoployUrl + "mo/land/landList";//获取地图地块

    public static String getRichList = MonoployUrl + "mo/land/rankingList";//富豪榜


    public static String myLand = MonoployUrl + "mo/land/myLand";//我的地产

    public static String landRecordList = MonoployUrl + "mo/land/landRecordList";//圈地记录


    public static String transferLand = MonoployUrl + "mo/land/transferLand";//地块转让

    public static String riseLand = MonoployUrl + "mo/land/riseLand";//地块升级

    public static String renameLand = MonoployUrl + "mo/land/renameLand";//地块重命名

    public static String extendLand = MonoployUrl + "mo/land/extendLand";//地块延期

    public static String createRiseOrder = MonoployUrl + "mo/landOrder/createRiseOrder";//创建升级地块订单

    public static String selectIncomeRecord = MonoployUrl + "mo/winLog/selectIncomeRecord";//查询摇号收益记录

    public static String rulesList = MonoployUrl + "mo/landRules/rulesList";//期限列表


    public static String deciveTokenSave = MonoployUrl + "mo/deciveToken/save";//保存设备信息

    public static String rebateList = URL + "Api/PaymentMonopoly/rebateList";//收益列表(地产)

    public static String paymentMonopoly = URL + "Api/PaymentMonopoly/pay";//支付购买地块

    public static String validatePassword = URL + "Api/PaymentMonopoly/validatePassword";//升级地块验证密码


    public static String payment = URL + "Api/PaymentForMining/payment";//申请手机节点付款

    public static String getEarnings = URL + "Api/PaymentMonopoly/getEarnings";//收益清单--地产收益

    public static String getBlockReward = URL + "Api/DbmbpEarnings/getEarnings";//出块收益

    public static String getEarningsUnions = URL + "Api/PaymentMonopoly/getEarningsUnions";//收益清单--地产链接收益

    public static String getPhoneNodeEarnLogs = URL + "Api/Member/getPhoneNodeEarnLogs";//收益清单--节点链接收益


    public static String findStepNumByUid = URL + "Api/UserDayLimit/findStepNumByUid" ;//查询用户提现或兑换阶梯额度 以及 vip 等级

    public static String countStepFeeByUid = URL + "Api/UserDayLimit/countStepFeeByUid";//  计算手续费（只用于前端验证）

    public static String notice_details = URL +"Api/Message/detail"; //公告详情接口

    public static String paymentForUpdateMining = URL+"Api/PaymentForMining/paymentForUpdateMining"; //为矿机升级付款接口

    public static String getNewMessage = URL+ "Api/Message/getNewMessage";//首页公告弹窗

    public static String hasBeenRead = URL+ "Api/Message/read";//首页公告已读

    public static String applyWithdraw = URL+ "Api/PaymentForMining/applyWithdraw";//矿机提币接口
    public static String moneyInfo = URL+ "Api/PaymentForMining/moneyInfo";//查询可提现余额
    public static String applyWithdrawPre = URL+ "Api/PaymentForMining/applyWithdrawPre";//提现手续费详情

    public static String applyRealNameAuth = URL+ "api/Info/applyRealNameAuth";//申请实名认证接口

    public static String findRealNameAuth = URL+ "api/Info/findRealNameAuth";//获取实名认证信息

    public static String updateIdImage = URL+ "api/Info/updateIdImage";//获取实名认证信息









}

