package com.tymall.utils.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.tymall.R;

//上架GooglePlay SSL证书错误问题
public class GooglePlayUtil {

    public   void handleSslError(WebView view, SslErrorHandler handler, SslError error) {
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
