package com.yibao.biggirl.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/29 14:12
 */
public class LongPressPhoto {


    private void applyBobbleAnim(View targetView) {
        AnimationSet bobbleAnimSet = new AnimationSet(true);
        ScaleAnimation expand = new ScaleAnimation(0.8f,
                                                   1.0f,
                                                   0.8f,
                                                   1.0f,
                                                   Animation.RELATIVE_TO_SELF,
                                                   0.5f,
                                                   Animation.RELATIVE_TO_SELF,
                                                   0.5f);
        expand.setDuration(300);

        bobbleAnimSet.addAnimation(expand);
        bobbleAnimSet.setFillAfter(true);
        bobbleAnimSet.setInterpolator(new OvershootInterpolator());

        targetView.startAnimation(bobbleAnimSet);
    }


}
