package com.tymall.mvp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.aries.ui.widget.progress.UIProgressDialog;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.utils.LocalManageUtil;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by wds on 2018/6/22.
 */

/**
 * Activity的base基类
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseActivity<V extends GEMUI, P extends ActPresenter<V>> extends MVPActivity<V, P>
        implements GEMUI {

    private UIProgressDialog dialog;

    private static final ArrayList<BaseActivity> sBaseActivities = new ArrayList<>();

    private CompositeDisposable compositeDisposable; //OKgo Rxjava  关闭页面停止加载


    /**
     * 关闭所有需要登录的activity
     */
    public static void finishAllNeedLoginActivities() {
        List<BaseActivity> baseActivities = (ArrayList<BaseActivity>) sBaseActivities.clone();
        Iterator<BaseActivity> it = baseActivities.iterator();
        while (it.hasNext()) {
            BaseActivity baseActivity = it.next();
            if (baseActivity.isNeedLogin()) {
                baseActivity.finish();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBaseActivities.add(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }


    @Override
    public void hideStatus() {

    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    @Override
    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }

    }


    @Override
    public void showProgress(String title, String content) {
        showProgress(title, title, true, false, false);
    }

    @Override
    public void showProgress(String title, String content, boolean cancelable, boolean cancelableOnTouchOutside, boolean closePageWhenCanceled) {
        if (!isAlive()) {
            return;
        }


        if (dialog == null) {
            dialog = createProgressDialog(title, content);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
            if (cancelable && closePageWhenCanceled) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        BaseActivity.this.finish();
                    }
                });
            }
        }
        dialog.show();
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
    public void dismissProgress() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    dialog = null;
                }
            }, Constant.DISMISS_TIME);



        }catch (Exception e){

        }
    }

    @Override
    public void finish() {
        super.finish();
        if (dialog != null &&  dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode, resultCode, data);
        return;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getPresenter().onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 请求权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constant.REQ_CODE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0].equals(Manifest.permission.CAMERA)) {
                    // EventBus.getDefault().post(new ScanEvent());
                }
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // EventBus.getDefault().post(new MassiteEvent());
                }
                return;
            }
        }
    }



    @Override
    protected void onDestroy() {
        sBaseActivities.remove(this);
        super.onDestroy();
        ToastUtils.cancelToast();
        XToastUtils.cancelToast();
        dispose();
    }

    /**
     * 是否需要登录
     *
     * @return
     */
    protected boolean isNeedLogin() {
        return true;
    }
}
