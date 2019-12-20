package com.tymall.utils.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.coolindicator.sdk.CoolIndicator;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by wds on 201811/12.
 */


/**
 * 当前 WebView 加载网页进度
 *
 *
 *
 */
public class BaseWebChromeClient extends WebChromeClient {
    String mAccount = null;
    Context mContext;
    String mInfo = null;
    CoolIndicator mProgressBar;
    TextView mTextView;

    public BaseWebChromeClient(Context context, CoolIndicator progress, TextView textView, String info, String account) {
        this.mContext = context;
        this.mProgressBar = progress;
        this.mTextView = textView;
        this.mInfo = info;
        this.mAccount = account;
    }

    public BaseWebChromeClient(Context context, CoolIndicator progress, TextView textView) {
        this.mContext = context;
        this.mProgressBar = progress;
        this.mTextView = textView;
    }

    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
            mProgressBar.complete();
//            if (this.mAccount != null) {
//                view.loadUrl("javascript:getEosAccount('" + this.mAccount + "')");
//            }
//            if (this.mInfo != null) {
//                view.loadUrl("javascript:getWalletWithAccount('" + this.mInfo + "')");
//                return;
//            }
            return;
        }
        this.mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.start();
        this.mProgressBar.setProgress(newProgress);
    }

    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
       // this.mTextView.setText(title);
    }

    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }



}