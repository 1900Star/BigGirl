package com.yibao.biggirl.mvp.app;

import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;
import com.yibao.biggirl.model.android.ResultsBeanX;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:04
 */
public interface AppContract {
     interface View
            extends BaseView<Presenter>
    {
        void loadData(List<ResultsBeanX> list);
        void refresh(List<ResultsBeanX> list);
        void loadMore(List<ResultsBeanX> list);

        void showError();
        void showNormal();

    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page,String type,int status);
    }
}
