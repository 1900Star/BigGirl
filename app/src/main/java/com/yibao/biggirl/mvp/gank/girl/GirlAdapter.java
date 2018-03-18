package com.yibao.biggirl.mvp.gank.girl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.biggirl.base.listener.HideToolbarListener;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.view.ZoomImageView;

import java.util.List;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class GirlAdapter
        extends android.support.v4.view.PagerAdapter {
    private Context mContext;
    private View mCurrentView;
    private List<String> mList;
    private boolean isShowBar = true;


    public GirlAdapter(Context context, List<String> list) {

        this.mContext = context;
        this.mList = list;
    }


    @Override
    public int getCount() {

        return mList == null
                ? 0
                : mList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ZoomImageView view = ImageUitl.creatZoomView(mContext);
        ImageUitl.loadPic(mContext, mList.get(position), view);
        view.setOnClickListener(view1 -> {

            if (mContext instanceof HideToolbarListener) {
                ((HideToolbarListener) mContext).hideOrShowAppbar();
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {

        return mCurrentView;
    }


}
