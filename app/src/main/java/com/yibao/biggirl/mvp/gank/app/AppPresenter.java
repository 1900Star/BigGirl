package com.yibao.biggirl.mvp.gank.app;

import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.app.AppDataSource;
import com.yibao.biggirl.model.app.RemoteAppData;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class AppPresenter
        implements AppContract.Presenter
{
    private AppContract.View mView;
    private RemoteAppData    mRemoteAppData;

    public AppPresenter(AppContract.View view) {
        this.mView = view;
        mRemoteAppData = new RemoteAppData();
        mView.setPrenter(this);
    }


    @Override
    public void start(String type, int codeId) {
        loadData(20, 1, type, Constants.LOAD_DATA);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void loadData(int size, int page, String type, final int status) {

        mRemoteAppData.getApp(size, page, type, new AppDataSource.LoadADataCallback() {
            @Override
            public void onLoadData(List<ResultsBeanX> list) {
                if (status == Constants.LOAD_DATA) {
                    mView.loadData(list);
                } else if (status == Constants.REFRESH_DATA) {
                    mView.refresh(list);
                } else if (status == Constants.LOAD_MORE_DATA) {
                    mView.loadMore(list);
                }

                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });


    }


}
