package com.yibao.biggirl.mvp.gank.app;

import com.yibao.biggirl.model.app.AppDataSource;
import com.yibao.biggirl.model.app.RemoteAppData;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.network.GirlService;
import com.yibao.biggirl.util.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * @ Author: Luoshipeng
 * @ Name:   AppPresenterTest
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/5/16/ 22:20
 * @ Des:    //TODO
 */
public class AppPresenterTest {
    AppPresenter mAppPresenter;
    @Mock
    AppContract.View mView;
    @Mock
     RemoteAppData mRemoteAppData;



    @Test
    public void loadData() {
        mRemoteAppData.getApp(20, 1, "福利", new AppDataSource.LoadDataCallback() {
            @Override
            public void onLoadData(List<ResultsBeanX> list) {
                    mView.loadData(list);
//                if (status == Constants.LOAD_DATA) {
//                } else if (status == Constants.REFRESH_DATA) {
//                    mView.refresh(list);
//                } else if (status == Constants.LOAD_MORE_DATA) {
//                    mView.onLoadMore(list);
//                }
//
//                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });


    }

    @Test
    public void subscribe() {
    }

    @Test
    public void unsubscribe() {
    }

    @After
    public void tearDown() {
    }
}