package com.yibao.biggirl.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * 作者：Stran on 2017/3/28 02:24
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class AnimationUtil {
    private static final int startY = 0;
    private final static int deltaY = 100;

    public static Animator upTranslateY(final View view) {

        final ValueAnimator animator = ValueAnimator.ofInt(0, 1)
                                                    .setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = animator.getAnimatedFraction();
                view.scrollTo(0, startY + (int) (deltaY * fraction));

            }
        });
        animator.start();
        return animator;
    }

    public static Animator downTranslateY(final View view) {

        final ValueAnimator animator = ValueAnimator.ofInt(0, 1)
                                                    .setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = animator.getAnimatedFraction();
                view.scrollTo(0, -(startY + (int) (deltaY * fraction))/2+2);

            }
        });
        animator.start();
        return animator;
    }


}
