package com.tymall.mvp;

import android.os.Bundle;


/**
 * Created by wpy on 2017/7/22.
 */

/**
 * Fragment的base基类
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseFragment<V extends GEMUI, P extends FragmentPresenter<V>> extends MVPFragment<V, P>
        implements GEMUI {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.onUIReady((MVPActivity) getActivity(), getUI());
        presenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initViews();



    @Override
    public void showProgress(String title, String content) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgress(title, content);
    }

    @Override
    public void showProgress(String title, String content, boolean cancelable, boolean cancelableOnTouchOutside, boolean closePageWhenCanceled) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgress(title, content, cancelable, cancelableOnTouchOutside, closePageWhenCanceled);
    }

    @Override
    public void dismissProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).dismissProgress();
    }

}