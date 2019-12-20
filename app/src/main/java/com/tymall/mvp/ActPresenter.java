package com.tymall.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Delegate all control functions of activity.
 * And handle all business logic in this class
 * Created by wpy on 2017/7/22.
 */

public class ActPresenter<V extends GEMUI> extends AbstractPresenter<V> {




    @Override
    public void onUIReady(MVPActivity activity, V ui) {
        super.onUIReady(activity, ui);

    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onNewIntent(Intent intent) {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

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

}
