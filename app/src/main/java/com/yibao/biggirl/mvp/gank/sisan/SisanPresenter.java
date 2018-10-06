package com.yibao.biggirl.mvp.gank.sisan;

import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.model.sisanjiujiu.RemoteSisanjiujiu;
import com.yibao.biggirl.model.sisanjiujiu.SisanDataSource;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class SisanPresenter
        implements SisanContract.Presenter {
    private SisanContract.View<Girl> mView;
    private SisanContract.View<Girl> mSisanActiviy;
    private RemoteSisanjiujiu mRemoteSisanjiujiu;


    public SisanPresenter(SisanContract.View<Girl> view) {
        this.mView = view;
        this.mSisanActiviy = view;
        mRemoteSisanjiujiu = new RemoteSisanjiujiu();
    }


    @Override
    public void start(String url, int page) {
        loadData(url, page, Constants.LOAD_DATA);
    }


    @Override
    public void loadData(String url, int page, int loadType) {
        mRemoteSisanjiujiu.getSisan(url, page, new SisanDataSource.LoadSisanCallback() {
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
    public void loadDataList(String url, int loadType) {
        mRemoteSisanjiujiu.getSisanSingle(url, new SisanDataSource.LoadSisanCallback() {
            @Override
            public void onLoadDatas(List<Girl> urlList) {
                if (loadType == Constants.REFRESH_DATA) {
                    mSisanActiviy.refresh(urlList);
                } else if (loadType == Constants.LOAD_DATA) {
                    mSisanActiviy.loadData(urlList);
                } else if (loadType == Constants.LOAD_MORE_DATA) {
                    mSisanActiviy.loadMore(urlList);
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
        mView = null;
        mSisanActiviy = null;
    }

}
