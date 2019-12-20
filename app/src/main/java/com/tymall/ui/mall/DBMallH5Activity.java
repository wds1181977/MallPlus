package com.tymall.ui.mall;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.base.XBaseActivity;
import com.tymall.model.HttpModel;

import com.tymall.ui.event.EosEvent;
import com.tymall.ui.fragment.PayFragment;
import com.tymall.ui.fragment.TransferOutDialogFragment;
import com.tymall.utils.CacheUtils;
import com.tymall.utils.LogUtil;
import com.tymall.utils.NetUtils;
import com.tymall.utils.SP;
import com.tymall.utils.StringUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.utils.webview.ScrollWebView;
import com.tymall.view.XProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.Map;


/**
 * <pre>
 *     @author wds
 *     desc  : DBMall H5 外部链接跳转的页面
 *     revise: 原生webView
 * </pre>
 */

public class DBMallH5Activity extends XBaseActivity implements PayFragment.PayListener {


    private FrameLayout videoFullView;
    private LinearLayout llWebView;
    private ScrollWebView mWebView;
    private XProgressBar pb;
    public String url, title;
    private MyWebChromeClient webChromeClient;
    private View mErrorView;
    //是否可以调用app的js交互
    public boolean isJsToAppCallBack = true;
    private boolean isHaveMsg = false;

    private PayFragment mPayFrgment;
    private Bundle mBundle;
    private static boolean isNoToken = false;

    private static boolean isOnlyUserId = false;


    private static boolean isFromShop = false;

    private String dbMallToken = "";

    private TransferOutDialogFragment transferOutDialogFragment = null;


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;


    /**
     * 1 响应h5支付界面
     * 2 申请手机节点付款
     */
    private int typeFlag;



    private String refreshUrl, num,phone,trferJson = "";
    private Map<String ,String> jsonmap = null;

    public static void openH5(Activity activity, String url, String title) {
        if (activity != null) {
            isFromShop = false;
            isNoToken = false;
            Intent intent = new Intent(activity, DBMallH5Activity.class);
            intent.putExtra(Constant.URL, url);
            intent.putExtra(Constant.MALL_TITLE, title);
            activity.startActivity(intent);
        }
    }

    /**
     * 来源：购物车跳转而来
     * 专用
     *
     * @param activity
     * @param url
     * @param title
     */
    public static void openH5ForShop(Activity activity, String url, String title) {
        if (activity != null) {
            isFromShop = true;
            isNoToken = false;
            isOnlyUserId = false;

            Intent intent = new Intent(activity, DBMallH5Activity.class);
            intent.putExtra(Constant.URL, url);
            intent.putExtra(Constant.MALL_TITLE, title);
            activity.startActivity(intent);
        }
    }

    public static void openH5NoToken(Activity activity, String url) {
        if (activity != null) {
            isFromShop = false;
            isNoToken = true;
            isOnlyUserId = false;

            Intent intent = new Intent(activity, DBMallH5Activity.class);
            intent.putExtra(Constant.URL, url);
            activity.startActivity(intent);
        }
    }

    public static void openH5OnlyUid(Activity activity, String url) {
        if (activity != null) {
            isFromShop = false;
            isNoToken = false;
            isOnlyUserId = true;
            Intent intent = new Intent(activity, DBMallH5Activity.class);
            intent.putExtra(Constant.URL, url);
            activity.startActivity(intent);
        }
    }

    public static void openH5NoTokenNew(Activity activity, String url, String dbMallToken, int fragmentId) {
        if (activity != null) {
            isFromShop = false;
            isNoToken = true;
            isOnlyUserId = false;
            Intent intent = new Intent(activity, DBMallH5Activity.class);
            intent.putExtra(Constant.URL, url);
            intent.putExtra("dbMallToken", dbMallToken);
            intent.addFlags(fragmentId);
            activity.startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbmall_h5;
    }

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        EventBus.getDefault().register(this);
        if (mPayFrgment == null) {
            mPayFrgment = PayFragment.getInstance(mBundle = new Bundle());

        }
        if (transferOutDialogFragment == null) {
            transferOutDialogFragment = TransferOutDialogFragment.getInstance(mBundle = new Bundle());
        }

        initFindById();
        initIntentData();
        initWebView();
    }


    private void initFindById() {
        llWebView = findViewById(R.id.ll_web_view);
        mWebView = findViewById(R.id.webView);
        pb = findViewById(R.id.pb);
        llWebView.setVisibility(View.VISIBLE);
    }


    public void initIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constant.URL);
            title = intent.getStringExtra(Constant.MALL_TITLE);
            dbMallToken = intent.getStringExtra("dbMallToken");
        }
    }


    private String getMarketUrl() {
        String token = CacheUtils.getDBMallToken();
        if (isFromShop) {
            return new StringBuilder(url).append(token).append("&mall_title=").append(cnToUTF_8(title)).append("&typeDetail=1").toString();
        } else {
            return new StringBuilder(url).append(token).append("&mall_title=").append(cnToUTF_8(title)).toString();
        }
    }

    private static String cnToUTF_8(String cn) {
        if (TextUtils.isEmpty(cn)) {
            return "";
        }
        try {

            //用默认字符编码解码字符串。
            byte[] bs = cn.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, "UTF-8");

        } catch (Exception e) {
            return "";
        }


    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        WebSettings ws = mWebView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        //网页在加载的时候暂时不加载图片，当所有的HTML标签加载完成时
        //在加载图片具体的做法如下初始化webView的时候设置不加载图
        ws.setBlockNetworkImage(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        mWebView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //如果启用了JavaScript，要做好安全措施，防止远程执行漏洞
        removeJavascriptInterfaces(mWebView);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        //自动加载图片
        ws.setLoadsImagesAutomatically(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);
        // webView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /*设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);
        mWebView.addJavascriptInterface(new WebViewInterface(getContext()), "Android");
        mWebView.setDownloadListener(new MyWebViewDownLoadListener(this));//点击链接下载文档，，

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(webChromeClient = new MyWebChromeClient());
        mWebView.setScrollWebListener(new ScrollWebView.OnScrollWebListener() {
            @Override
            public void onScroll(int dx, int dy) {
                //WebView的总高度
                float webViewContentHeight = mWebView.getContentHeight() * mWebView.getScale();
                //WebView的现高度
                float webViewCurrentHeight = (mWebView.getHeight() + mWebView.getScrollY());

                if ((webViewContentHeight - webViewCurrentHeight) == 0) {

                }
            }
        });

        if (!TextUtils.isEmpty(url)) {

          LogUtil.d("@@@", "url = :" + url);
                mWebView.loadUrl(url);

        }

    }


    /**
     * webView下载类
     */
    public class MyWebViewDownLoadListener implements DownloadListener {

        private Context mContext;

        public MyWebViewDownLoadListener(Context mContext){
            this.mContext=mContext;
        }
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {

            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }


    public class WebViewInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebViewInterface(Context c) {
            mContext = c;
        }

        /**
         * 响应h5页面的返回界面
         * 退出
         */
        @android.webkit.JavascriptInterface
        public void goBackHome() {
            LogUtil.d("wds mall goBackHome ");

            if (!isActive()) {
                return;
            }

            DBMallH5Activity.this.finish();

        }


        /**
         * 响应h5页面的返回界面
         * 跳转到原生
         */
        @android.webkit.JavascriptInterface
        public void skipMode(int mode) {
            LogUtil.d("wds mall goShoppingCart ", mode + "");
            if (mode == 301) {//301代表新加入商品进购物车，然后让购物车刷新
                CacheUtils.setIsRefreshShoppingCart("301");
            }
            if (!isActive()) {
                return;
            }
            if (mode == 200) {
                DBMallH5Activity.this.finish();
                return;
            }
            if (mode > 100) {
                SP.set(Constant.SKIP_MODE, mode);
                if (mode < 200) {
                    SP.set(Constant.SKIP_MODE_NEED_FINISH, true);
                    DBMallH5Activity.this.finish();
                }
            }

        }



    }


    private void showTxDialog() {
        mBundle.putSerializable(Constant.DBM_JSON_MAP, (Serializable)jsonmap);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().remove(transferOutDialogFragment).commitAllowingStateLoss();
        transferOutDialogFragment.show(manager, "transferOutDialogFragment");
    }






    public void loadSuccessUrl(String orderUrl) {

        String token = CacheUtils.getDBMallToken();
        String url = new StringBuilder(HttpModel.getMarketPaySuccess)
                .append("?t=" + token).append("&o=" + orderUrl).toString();
        LogUtil.d("dbmall_url", url);
        this.mWebView.loadUrl(url);

    }


    @Subscribe
    public void onEvent(EosEvent event) {
        if (event.getStatus() == EosEvent.STATUS_IS_LOGIN_SUCCESS) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAlive() && mWebView != null && getMarketUrl() != null) {
                        mWebView.loadUrl(getMarketUrl());
                    }
                }
            });
        }else if(event.getStatus() == EosEvent.H5_URL) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAlive() && mWebView != null && !TextUtils.isEmpty(refreshUrl)) {
                        String webUrl = refreshUrl + "?uid=" + StringUtils.getUserId() + "&userId=" + StringUtils.getUserId();
                        mWebView.loadUrl(webUrl);
                    }
                }
            });
        }else if(event.getMode() != null && event.getFromCode() == Constant.FROM_H5){
            String status = event.getMode();
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (status.equals("10")) {
                showTxDialog();
            } else {



            }

        }


    }



    @Override
    public void paySuccess(String pid, String currency_id, String pay_password, String R2, String type) {

    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.clearHistory();
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.destroy();
            mWebView = null;
        }
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }



    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 201) {
            webChromeClient.mUploadMessageForAndroid5(intent, resultCode);
        }


    }



    /**
     * 监听网页链接:
     * 优酷视频直接跳到自带浏览器
     * 根据标识:打电话、发短信、发邮件
     * 添加javascript监听
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            addImageClickListener();
            super.onPageFinished(view, url);
            //在html标签加载完成之后在加载图片内容
            mWebView.getSettings().setBlockNetworkImage(false);
        }


        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            if (newScale - oldScale > 7) {
                //异常放大，缩回去。
                view.setInitialScale((int) (oldScale / newScale * 100));
            }
        }

        // 向主机应用程序报告Web资源加载错误。这些错误通常表明无法连接到服务器。
        // 值得注意的是，不同的是过时的版本的回调，新的版本将被称为任何资源（iframe，图像等）
        // 不仅为主页。因此，建议在回调过程中执行最低要求的工作。
        // 6.0 之后
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            }
            //ToastUtils.showToast("服务器异常6.0之后");
            //当加载错误时，就让它加载本地错误网页文件
            //mWebView.loadUrl("file:///android_asset/errorpage/error.html");

            showErrorPage();//显示错误页面
        }


        // 6.0 之前
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            }
            //ToastUtils.showToast("服务器异常6.0之前");
            //当加载错误时，就让它加载本地错误网页文件
            //mWebView.loadUrl("file:///android_asset/errorpage/error.html");

            showErrorPage();//显示错误页面
        }

        // 通知主机应用程序在加载资源时从服务器接收到HTTP错误。
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }


        // 通知主机应用程序已自动处理用户登录请求。
        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
        }

        // 在加载资源时通知主机应用程序发生SSL错误。
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            Context context = view.getContext();
            android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(context);
            String message = context.getString(R.string.ssl_error_message_default);
            int errorType = error.getPrimaryError();
            switch (errorType) {
                case SslError.SSL_UNTRUSTED:
                    message = context.getString(R.string.ssl_error_message_untrusted);
                    break;
                case SslError.SSL_EXPIRED:
                    message = context.getString(R.string.ssl_error_message_expired);
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = context.getString(R.string.ssl_error_message_id_mismatch);
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = context.getString(R.string.ssl_error_message_not_valid);
                    break;
            }
            message += " " + context.getString(R.string.ssl_error_wether_continue);
            dialogBuilder.setTitle(R.string.ssl_error_title);
            dialogBuilder.setMessage(message);

            dialogBuilder.setPositiveButton(R.string.string_sure_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            android.app.AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    boolean isContinue = false;

    /**
     * 隐藏加载对话框
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(DBMallH5Activity.this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        pb.startAnimation(animation);
    }

    /**
     * 获取消失的动画
     *
     * @param context
     * @return
     */
    private AnimationSet getDismissAnim(Context context) {
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }


    /**
     * 相关配置
     * 播放网络视频配置
     * 上传图片(兼容)
     */
    private class MyWebChromeClient extends WebChromeClient {

        private View progressVideo;
        private View customView;
        private CustomViewCallback customViewCallback;
        private ValueCallback<Uri[]> mUploadMessageForAndroid5;
        private ValueCallback<Uri> mUploadMessage;

        //监听h5页面的标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (title.contains("404") || title.contains("网页无法打开")) {
                showErrorPage();
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //如果没有网络直接跳出方法
            if (!NetUtils.isNetworkAvailable(DBMallH5Activity.this)) {
                return;
            }
            //如果进度条隐藏则让它显示
            if (View.INVISIBLE == pb.getVisibility()) {
                pb.setVisibility(View.VISIBLE);
            }
            //大于80的进度的时候,放慢速度加载,否则交给自己加载
            if (newProgress >= 80) {
                //拦截webView自己的处理方式
                if (isContinue) {
                    return;
                }
                pb.setCurProgress(100, 300, new XProgressBar.OnEndListener() {
                    @Override
                    public void onEnd() {
                        pb.setNormalProgress(100);
                        hideProgressWithAnim();
                        isContinue = false;
                    }
                });

                isContinue = true;
            } else {
                pb.setNormalProgress(newProgress);
            }

        }
        /*** 视频播放相关的方法 **/

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(DBMallH5Activity.this);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            showCustomView(view, callback);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//播放时横屏幕，如果需要改变横竖屏，只需该参数就行了
        }

        @Override
        public void onHideCustomView() {
            hideCustomView();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不播放时竖屏
        }


        // For Android > 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
            openFileChooserImplForAndroid5(uploadMsg);
            return true;
        }

        private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
            mUploadMessageForAndroid5 = uploadMsg;
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");

            DBMallH5Activity.this.startActivityForResult(chooserIntent, 201);
        }

        /**
         * 5.0以下 上传图片成功后的回调
         */
        public void mUploadMessage(Intent intent, int resultCode) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }

        /**
         * 5.0以上 上传图片成功后的回调
         */
        void mUploadMessageForAndroid5(Intent intent, int resultCode) {
            if (null == mUploadMessageForAndroid5) {
                return;
            }
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }

    }


    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        DBMallH5Activity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        mWebView.setVisibility(View.VISIBLE);
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 例如，该案例中链接来于喜马拉雅，支付宝，购物网站等等，就需要注意程序漏洞
     * 如果启用了JavaScript，务必做好安全措施，防止远程执行漏洞
     *
     * @param webView webView控件
     */
    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(11)      //支持api11以上
    private void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }






    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    boolean mIsErrorPage;

    private void showErrorPage() {
        try {
            FrameLayout webParentView = (FrameLayout) mWebView.getParent();
            initErrorPage();//初始化自定义页面
            if (webParentView != null) {
                while (webParentView.getChildCount() > 1) {
                    webParentView.removeViewAt(0);
                }
            }
            @SuppressWarnings("deprecation")
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
            webParentView.addView(mErrorView, 0, lp);
            mIsErrorPage = true;
        } catch (Exception e) {

        }
    }


    /**
     * 把系统自身请求失败时的网页隐藏
     */
    protected void hideErrorPage() {
        LinearLayout webParentView = (LinearLayout) mWebView.getParent();
        mIsErrorPage = false;
        if (webParentView != null) {
            while (webParentView.getChildCount() > 1) {
                webParentView.removeViewAt(0);
            }
        }
    }


    /**
     * 显示加载失败时自定义的网页
     */
    protected void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.layout_view_webview_data_error, null);
            LinearLayout ll_error_view = (LinearLayout) mErrorView.findViewById(R.id.ll_error_view);
            ll_error_view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (NetUtils.isNetworkConnection(DBMallH5Activity.this)) {
                        //如果有网络，则刷新页面
                        mWebView.reload();
                        recreate();
                    } else {
                        //没有网络，不处理
                        XToastUtils.showRoundRectToast(getString(R.string.network_connection_error));
                    }
                }
            });
            mErrorView.setOnClickListener(null);
        }
    }


    private void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                //  "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
                "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
                "}" +
                "})()");

        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        /*mWebView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                "window.injectedObject.textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");*/
    }


}




