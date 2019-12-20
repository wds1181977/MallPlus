package com.tymall.mvp;

/**
 * Created by wpy on 2017/7/22.
 */

public abstract class AbstractPresenter<V extends GEMUI> implements IPresenter<V> {
    private MVPActivity activity;
    private V ui;

    @Override
    public MVPActivity getActivity() {
        return activity;
    }

    @Override
    public V getUI() {
        return ui;
    }

    @Override
    public String getString(int resId) {
        return activity.getResources().getString(resId);
    }

    @Override
    public void onUIReady(MVPActivity activity, V ui) {
        this.activity = activity;
        this.ui = ui;
    }

}
