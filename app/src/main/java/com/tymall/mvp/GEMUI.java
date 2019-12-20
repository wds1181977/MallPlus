package com.tymall.mvp;


import io.reactivex.disposables.Disposable;

/**
 * MVP - View interface
 * <p>
 * Created by wpy on 2017/7/22.
 */

public interface GEMUI{

    boolean isAlive();

    /**
     * whether is resumed or visible to user
     *
     * @return
     */
    boolean isActive();


    /**
     * Return layout resource for activity or fragment
     *
     * @return
     */
    int getContentLayout();



    void showProgress(String title, String content);

    void showProgress(String title, String content, boolean cancelable, boolean cancelableOnTouchOutside, boolean closePageWhenCanceled);

    void dismissProgress();

     void addDisposable(Disposable disposable);

     void dispose();

}
