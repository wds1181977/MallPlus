package com.tymall.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.db.CacheManager;
import com.tymall.R;
import com.tymall.utils.CustomDialog;
import com.tymall.utils.SharedPreferencesUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements BaseView {
    private View view;
    private Unbinder unbinder;
    public static Activity fragment;
    public Context cnt;

    private CompositeDisposable compositeDisposable;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            initView();
        }
        fragment = getActivity();
        return view;
    }

    public BaseFragment() {
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    public View getView() {
        return view;
    }

    @Override
    public void showLoading() {
        CustomDialog.getInstance(getActivity()).show();
    }

    @Override
    public void hideLoading() {
        CustomDialog.getInstance(getActivity()).dismiss();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showErr(String err) {
        showToast(cnt.getResources().getString(R.string.network_request_data_is_abnormal) + err);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        if (fragment != null) {
            fragment = null;
        }
        super.onDestroy();
        try {
            dispose();
            if(unbinder != null) {
                unbinder.unbind();
            }
            CustomDialog.getInstance(getActivity()).destoryDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cnt = context;
    }



    public boolean isHasCache(String httpUrl) {
        try {
            if (CacheManager.getInstance().
                    get(httpUrl +
                            SharedPreferencesUtils.getInstance().getShared("userId")) != null) {
                return true;

            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
