package com.yibao.biggirl.mvp.app;

import com.yibao.biggirl.base.BasePresenter;
import com.yibao.biggirl.base.BaseView;
import com.yibao.biggirl.model.android.AndroidAndGirl;

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
        void loadData(List<AndroidAndGirl> list);
        void refresh(List<AndroidAndGirl> list);
        void loadMore(List<AndroidAndGirl> list);

        void showError();
        void showNormal();

    }

    interface Presenter
            extends BasePresenter
    {
        void loadData(int size, int page,String type,int status);
    }
}
