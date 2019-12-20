package com.tymall.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class TextAnimatorUtil {

    public static void showTextAnimator(View view) {
        if (view == null) {
            return;
        }
        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(0)
                .playOn(view);
    }
}
