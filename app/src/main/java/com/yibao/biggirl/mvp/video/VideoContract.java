package com.yibao.biggirl.mvp.video;


import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;
import com.yibao.biggirl.base.LoadingPager;
import com.yibao.biggirl.model.video.VideoResultsBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:04
 */
public interface VideoContract {
    interface View
            extends BaseView<Presenter>
    {

        LoadingPager.LoadedResult loadData(List<VideoResultsBean> list);

        void refresh(List<VideoResultsBean> list);

        void loadMore(List<VideoResultsBean> list);

        void showError();

        void showNormal();

    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page, int type);
    }
}
