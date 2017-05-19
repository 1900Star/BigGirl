package com.yibao.biggirl.mvp.android;

import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.app.AppDataSource;
import com.yibao.biggirl.model.app.RemoteAppData;
import com.yibao.biggirl.mvp.app.AppContract;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */


class AndroidPresenter
        implements AppContract.Presenter
{
    private AppContract.View mView;
    private RemoteAppData    mRemoteData;

    public AndroidPresenter(AppContract.View view) {
        this.mView = view;
        mRemoteData = new RemoteAppData();
        mView.setPrenter(this);
    }


    @Override
    public void start(String type) {
        loadData(20, 1, type, Constants.LOAD_DATA);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void loadData(int page, int size, String type, int status) {
        mRemoteData.getApp(page, size, type, new AppDataSource.LoadADataCallback() {
            @Override
            public void onLoadData(List<AndroidAndGirl> list) {
                if (status == Constants.LOAD_DATA) {
                    mView.loadData(list);
                } else if (status == Constants.REFRESH_DATA) {
                    mView.refresh(list);
                } else if (status == Constants.PULLUP_LOAD_MORE_DATA) {
                    mView.loadMore(list);
                }

                //                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();

            }
        });
    }


}
