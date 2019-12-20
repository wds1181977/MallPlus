package com.tymall.utils.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tymall.R;



/**
 * Created by wds on 2018/11`/21.
 */

public class BaseWebViewClient extends WebViewClient {
    Context mContext;

    public BaseWebViewClient(Context context) {
        this.mContext = context;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        try {

            //view.loadUrl("javascript:" + IOUtils.toString(this.mContext.getAssets().open("scatter_pe.js"), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }



    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    private void handleSslError(WebView view, SslErrorHandler handler, SslError error) {
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


