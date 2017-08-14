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
    interface View
            extends BaseView<Presenter>
    {

        void loadData(List<String> list);

        void refresh(List<String> list);

        void loadMore(List<String> list);

        void showError();

        void showNormal();


    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page, int loadType, String dataType);

    }
}
