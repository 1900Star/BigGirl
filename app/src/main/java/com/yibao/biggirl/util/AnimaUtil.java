package com.yibao.biggirl.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * 作者：Stran on 2017/3/28 02:24
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class AnimaUtil {
    public static void applyBobbleAnim(View targetView) {
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
