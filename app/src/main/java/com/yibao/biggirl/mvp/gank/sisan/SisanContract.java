package com.yibao.biggirl.mvp.gank.sisan;

import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:04
 */
public interface SisanContract {

    interface View<T>
            extends BaseView<Presenter> {

        void loadData(List<T> list);

        void refresh(List<T> list);

        void loadMore(List<T> list);



    }


    interface Presenter
            extends BasePresenter
    {
        void loadData(String url, int page, int loadType);

        void loadDataList(String url, int loadType);

    }
}
