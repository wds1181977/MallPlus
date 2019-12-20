package com.tymall.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.SimpleImmersionFragment;
import com.tymall.R;
import com.tymall.utils.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
/**
 * 针对MainAcitivity沉浸式Fragment
 * 请在immersionBarEnabled方法中实现你的沉浸式代码，
 * @author wds
 * @date 2019 /3/26
 */

public abstract class XBaseFragment extends SimpleImmersionFragment implements BaseView {
    private View view;
    private Unbinder unbinder;
    public static Activity fragment;
    public Context cnt;
    protected Activity mActivity;


    public Toolbar toolbar;


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

    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cnt = context;
        mActivity = (Activity) context;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if(toolbar != null ) {
            ImmersionBar.setTitleBar(getActivity(), toolbar);
        }

        initView();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        toolbar = view.findViewById(R.id.toolbar);
        fragment = getActivity();
        return view;
    }

    public XBaseFragment() {
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
        dispose();
    }

    @Override
    public void onDestroy() {
        if (fragment != null) {
            fragment = null;
        }
        super.onDestroy();
        try {
            unbinder.unbind();
            CustomDialog.getInstance(getActivity()).destoryDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

