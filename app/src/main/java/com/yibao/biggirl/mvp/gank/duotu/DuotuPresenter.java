package com.yibao.biggirl.mvp.gank.duotu;

import com.yibao.biggirl.model.duotu.DuotuDataSource;
import com.yibao.biggirl.model.duotu.RemoteDuotuGirls;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class DuotuPresenter
        implements DuotuContract.Presenter {
    private DuotuContract.View<Girl> mView;
    private DuotuContract.View<Girl> mTuActiviy;
    private RemoteDuotuGirls mRemoteDuotuData;


    public DuotuPresenter(DuotuContract.View<Girl> view) {
        this.mView = view;
        this.mTuActiviy = view;
        mRemoteDuotuData = new RemoteDuotuGirls();
    }


    @Override
    public void start(String url, int page) {
        loadData(url, page, Constants.LOAD_DATA);
    }


    @Override
    public void loadData(String url, int page, int loadType) {
        mRemoteDuotuData.getDuotu(url, page, new DuotuDataSource.LoadDuotuCallback() {
            @Override
            public void onLoadDatas(List<Girl> urlList) {
                if (loadType == Constants.REFRESH_DATA) {
                    mView.refresh(urlList);
                } else if (loadType == Constants.LOAD_DATA) {
                    mView.loadData(urlList);
                } else if (loadType == Constants.LOAD_MORE_DATA) {
                    mView.loadMore(urlList);
                }

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadDataList(String url, int page,int loadType) {
        mRemoteDuotuData.getDuotuList(url, page, new DuotuDataSource.LoadDuotuCallback() {
            @Override
            public void onLoadDatas(List<Girl> urlList) {
                if (loadType == Constants.REFRESH_DATA) {
                    mTuActiviy.refresh(urlList);
                } else if (loadType == Constants.LOAD_DATA) {
                    mTuActiviy.loadData(urlList);
                } else if (loadType == Constants.LOAD_MORE_DATA) {
                    mTuActiviy.loadMore(urlList);
                }



            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

}
