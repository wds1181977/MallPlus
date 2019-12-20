package com.tymall.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.text.TextUtils;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tymall.easyhttp.RxEasyHttp;
import com.tymall.model.greendao.utils.GreenDaoManager;
import com.tymall.utils.LocalManageUtil;
import com.tymall.utils.LogUtil;
import com.tymall.utils.XToastUtils;
import com.tymall.utils.httpCallBack.MyHttpLoggingInterceptor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private static MyApplication mInstance;


    /**
     * 是否打开BugTag
     */

    public static boolean isOpenBugTag = false;

    /**
     * 是否能抓包
     */

    public static boolean isCanCatchBag = true;

    /**
     * 是否检测内存泄漏
     */
    public static boolean isSetUpLeakCanary = false;


    /**
     * 是否需要打印log
     */
    public static boolean isLog = true;


    private static Context mContext;

    /**
     * 当前版本名
     */
    public static String curVersion;


    /**
     * 打印Log  关键字
     */
    public static final String TAG_LOG = "dbank_log";

    /**
     * App 接口请求Debug 关键字
     */
    public static final String TAG_HTTP = "dbank";

    public static String UmengdeviceToken = "";


    public static Application getInstance() {
        return mInstance;
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.golden_font, R.color.white);//全局设置主题颜色
                layout.setHeaderInsetStart(0);
                return new MaterialHeader(context)
                        .setColorSchemeColors(mContext.getResources().getColor(android.R.color.black));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableAutoLoadMore(true);
                return new BallPulseFooter(context)
                        .setAnimatingColor(mContext.getResources().getColor(android.R.color.black))
                        .setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppConfig.isReleaseApk) {
            isOpenBugTag = false;
            isCanCatchBag = false;
            isLog = false;
            isSetUpLeakCanary = false;
        }
        mInstance = this;
        mContext = getApplicationContext();
        initOkGo();
        initX5WebView();
        GreenDaoManager.getInstance();
        getAppVersion();
        initBugly();
        setUmengInitialization();
       // Stetho.initializeWithDefaults(this);
        //initLeakCanary();
        XToastUtils.init(this);
        LocalManageUtil.setApplicationLanguage(getApplicationContext());
       // initBugTag();
        RxEasyHttp.getInstance().init(this);


    }

    private void initBugTag() {
        if (!isOpenBugTag) {
            return;
        }
        boolean isVisibility = true;
        int mode = Bugtags.BTGInvocationEventNone;
        if (isVisibility) {
            mode = Bugtags.BTGInvocationEventBubble;
        }

        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(false).//是否获取位置
                trackingCrashLog(true).//是否收集crash
                trackingConsoleLog(isLog).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                enableCapturePlus(true).
                build();

        Bugtags.addUserStep("custom step");

        Bugtags.start("6139f18f4edb7671b228d4f617d11236", this, mode, options);

    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LocalManageUtil.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }


    private void initLeakCanary() {
        if (!isSetUpLeakCanary) {
            return;
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取版本号
     */
    private void getAppVersion() {
        try {
            curVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化okgo
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor((TAG_HTTP), isLog);
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(MyHttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        //防止抓包
        if (!isCanCatchBag) {
            builder.proxySelector(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    return Collections.singletonList(Proxy.NO_PROXY);
                }

                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

                }
            });
        }


        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);

    }

    private void initX5WebView() {

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }



    /**
     * 初始化bugly
     */
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "193e3eb60c", isLog, strategy);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 友盟初始化
     */
    private void setUmengInitialization() {
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "98b27fef97c17bbc178a0558d1d476eb");
//        MobclickAgent.openActivityDurationTrack(false); // 禁止默认的页面统计方式，这样将不会再自动统计Activity
        // 打开统计SDK调试模式
        UMConfigure.setLogEnabled(false);
        // 设置日志加密
        UMConfigure.setEncryptEnabled(true);
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtil.e("@@@", "注册成功：deviceToken：-------->  " + deviceToken);
                UmengdeviceToken = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("@@@", "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });






        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                LogUtil.d("@@@", " launchApp msg.custom ====" + msg.getRaw());

                if(msg.getRaw() == null){
                    return;
                }
                try {



                }catch (Exception e){

                }

            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
                LogUtil.d("@@@", " openUrl msg.custom ====" + msg.getRaw());

            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                LogUtil.d("@@@", " openActivity msg.custom ====" + msg.getRaw());

            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                LogUtil.d("@@@", " dealWithCustomAction msg.custom ====" + msg.getRaw());

            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
}
