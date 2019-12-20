package com.tymall.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView> {

    protected WeakReference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mViewRef.get() != null;
    }
    /**
     * 获取连接的view
     */
    protected V getView() {
        return mViewRef.get();
    }
}