package com.yibao.biggirl.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @ Author: Luoshipeng
 * @ Name:   MainViewPager
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/4/30/ 19:46
 * @ Des:    //TODO
 */
public class FavoriteViewPager extends ViewPager {
    public FavoriteViewPager(@NonNull Context context) {
        super(context);
    }

    public FavoriteViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
