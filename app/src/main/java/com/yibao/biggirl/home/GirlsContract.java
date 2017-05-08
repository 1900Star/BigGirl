package com.yibao.biggirl.home;

import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;
import com.yibao.biggirl.model.girls.ResultsBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:04
 */
public interface GirlsContract {
    interface View
            extends BaseView<Presenter>
    {
        void loadData(List<ResultsBean> list);

        void refresh(List<ResultsBean> list);

        void loadMore(List<ResultsBean> list);

        void showError();

        void showNormal();


    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page, int loadType);

    }
}
