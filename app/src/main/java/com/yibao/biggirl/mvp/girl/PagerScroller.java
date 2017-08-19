package com.yibao.biggirl.mvp.girl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/18 19:39
 */
public class PagerScroller
        extends Scroller
{

    private int mScrollDuration;

    public void setDuration(int duration) {
        this.mScrollDuration = duration;
    }

    public PagerScroller(Context context) {
        super(context);

    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }


    public void initViewPagerScroll(ViewPager viewPager, int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            setDuration(duration);
            field.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
