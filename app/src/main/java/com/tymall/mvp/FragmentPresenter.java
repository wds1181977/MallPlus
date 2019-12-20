package com.tymall.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Delegate all control functions of Fragment.
 * And handle all business logic in this class
 * Created by wpy on 2017/7/22.
 */

public class FragmentPresenter<V extends GEMUI> extends AbstractPresenter<V> {
    private MVPFragment fragment;
    private boolean isResumed;
    private boolean isHidden;

    @Override
    public void onUIReady(MVPActivity activity, V ui) {
        super.onUIReady(activity, ui);
    }

    public void onCreate(Bundle savedInstanceState) {


    }

    /**
     * fragment 初始化时调用方法
     *
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {
    }

    public void onAttach(Context context, MVPFragment fragment) {
        this.fragment = fragment;
    }

    public void onStart() {

    }

    public void onResume() {
        isResumed = true;
    }

    public void onPause() {
        isResumed = false;
    }

    public void onDetach() {

    }

    public void onStop() {

    }


    public void onDestroy() {

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onDestroyView() {
    }


    public void onHiddenChanged(boolean hidden) {
        isHidden = hidden;
    }

    public MVPFragment getFragment() {

        return fragment;
    }

    public boolean isVisible() {
        return isResumed && !isHidden;
    }
}
