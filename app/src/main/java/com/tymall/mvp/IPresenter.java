package com.tymall.mvp;


/**
 * Created by wpy on 2017/7/22.
 */

public interface IPresenter<V extends GEMUI> {
    MVPActivity getActivity();

    V getUI();

    String getString(int resId);

    void onUIReady(MVPActivity activity, V ui);
}
