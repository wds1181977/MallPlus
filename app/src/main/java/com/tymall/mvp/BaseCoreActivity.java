package com.tymall.mvp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;




/**
 * Real base act for whole project
 * Created by wpy on 2017/7/22.
 */

public class BaseCoreActivity extends AppCompatActivity  {
    private boolean isAlive;
    private boolean isActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAlive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        isAlive = false;
        super.onDestroy();
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isAlive() {
        return isAlive && !isFinishing();
    }




}
