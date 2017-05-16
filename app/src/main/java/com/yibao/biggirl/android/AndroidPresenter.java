package com.yibao.biggirl.android;

import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.android.AndroidDataSource;
import com.yibao.biggirl.model.android.RemoteAndroidData;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class AndroidPresenter
        implements AndroidContract.Presenter
{
    private AndroidContract.View mView;
    private RemoteAndroidData    mRemoteAndroidData;

    public AndroidPresenter(AndroidContract.View view) {
        this.mView = view;
        mRemoteAndroidData = new RemoteAndroidData();
        mView.setPrenter(this);
    }


    @Override
    public void start() {
        loadData(20, 1, Constants.LOAD_DATA);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void loadData(int page, int size,int type) {
        mRemoteAndroidData.getAndroid(page, size, new AndroidDataSource.LoadADataCallback() {
            @Override
            public void onLoadData(List<AndroidAndGirl> list) {
                if (type == Constants.LOAD_DATA) {
                    mView.loadData(list);
                } else if (type == Constants.REFRESH_DATA) {
                    mView.refresh(list);
                } else if (type == Constants.PULLUP_LOAD_MORE_DATA) {
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
