package com.tymall.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.aries.ui.widget.progress.UIProgressDialog;
import com.bugtags.library.Bugtags;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.utils.LocalManageUtil;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity implements BaseView {
    private UIProgressDialog dialog;
    private boolean isAlive;
    private boolean isActive;
    private CompositeDisposable compositeDisposable; //OKgo Rxjava  关闭页面停止加载


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAlive = true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }

    }


    public void showProgress(String title, String content) {
        showProgress(title, content, true, false, false);
    }


    public void showProgress(String title, String content, boolean cancelable, boolean cancelableOnTouchOutside, boolean closePageWhenCanceled) {
        if (!isAlive()) {
            return;
        }


        dialog = createProgressDialog(title, content);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        if (cancelable && closePageWhenCanceled) {
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }
        dialog.show();
    }


    public void dismissProgress() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isAlive() && dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    dialog = null;
                }
            }, Constant.DISMISS_TIME);


        } catch (Exception e) {

        }
    }


    protected UIProgressDialog createProgressDialog(String title, String content) {
        String message = getString(R.string.loading_msg);

        UIProgressDialog dialog = new UIProgressDialog.MaterialBuilder(this)
                .setMessage(TextUtils.isEmpty(content) ? message : content)
                .setBackgroundRadiusResource(R.dimen.dp_radius_loading)
                .create();
        dialog.setTitle(TextUtils.isEmpty(title) ? "" : title);
        dialog.setDimAmount(0.6f);
        return dialog;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
        isActive = true;
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
        Bugtags.onPause(this);


    }

    @Override
    protected void onDestroy() {
        isAlive = false;
        super.onDestroy();
        ToastUtils.cancelToast();
        XToastUtils.cancelToast();
        dispose();
    }

    @Override
    public void finish() {
        super.finish();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Bugtags.onDispatchKeyEvent(this, event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void showLoading() {
        showProgress("", "");
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(msg);
            }
        });
    }

    @Override
    public void showErr(String err) {
        showToast(getApplicationContext().getResources().getString(R.string.network_request_data_is_abnormal) + err);

    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (dialog != null && dialog.isShowing()) {
            dispose();
        }

    }


    public boolean isActive() {
        return isActive;
    }

    public boolean isAlive() {
        return isAlive && !isFinishing();
    }

}
