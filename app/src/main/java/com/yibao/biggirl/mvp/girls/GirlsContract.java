package com.yibao.biggirl.mvp.girls;

import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:04
 */
public interface GirlsContract {
    interface View<T>
            extends BaseView<Presenter>
    {

        void loadData(List<T> list);

        void refresh(List<T> list);

        void loadMore(List<T> list);

        void showError();

        void showNormal();


    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page, int loadType, String dataType);

    }
}
