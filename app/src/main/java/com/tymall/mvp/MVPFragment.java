package com.tymall.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by wpy on 2017/7/22.
 */

public abstract class MVPFragment<V extends GEMUI, P extends FragmentPresenter<V>> extends BaseCoreFragment {
    protected P presenter;
    private Unbinder unbinder;

    private V ui;

    public Context cnt;

    public P getPresenter() {
        return presenter;
    }

    public V getUI() {
        return ui;
    }

    protected abstract P createPresenter();

    protected abstract V createUI();

    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (presenter == null) {
            presenter = createPresenter();
        }
        cnt = context;
        presenter.onAttach(context, this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    protected void setRootView(View rootView) {
        super.setRootView(rootView);
    }

    @Override
    protected void executeOnceAfterCreateView() {
        //        Assert.judge(getActivity() instanceof MVPActivity, "Please extend MVPActivity");
        //        presenter.onUIReady((MVPActivity) getActivity(), getUI());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        presenter.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ToastUtils.cancelToast();
        XToastUtils.cancelToast();
        presenter.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // NOTE: This method could not be presented by presenter layer
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(ui.getContentLayout(), container, false);
            unbinder = ButterKnife.bind(this, view);
            ui = createUI();

        }

        return view;

    }




}
