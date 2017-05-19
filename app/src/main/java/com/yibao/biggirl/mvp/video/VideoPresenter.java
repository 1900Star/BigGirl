package com.yibao.biggirl.mvp.video;

import com.yibao.biggirl.model.video.RemoteVideoData;
import com.yibao.biggirl.model.video.VideoBean;
import com.yibao.biggirl.model.video.VideoDataSource;
import com.yibao.biggirl.util.Constants;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class VideoPresenter
        implements VideoContract.Presenter
{
    private VideoContract.View mView;
    private RemoteVideoData    mRemoteVideoData;

    public VideoPresenter(VideoContract.View view) {
        this.mView = view;
        mRemoteVideoData = new RemoteVideoData();
        mView.setPrenter(this);
    }


    @Override
    public void start(String type) {
        loadData(20, 1, Constants.LOAD_DATA);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }


    @Override
    public void loadData(int size, int page, int type) {
        mRemoteVideoData.getVideo(size, page, new VideoDataSource.LoadVDataCallback() {
            @Override
            public void onLoadDatas(VideoBean videoBean) {
                if (type == Constants.LOAD_DATA) {
                    mView.loadData(videoBean.getResults());
                } else if (type == Constants.REFRESH_DATA) {
                    mView.refresh(videoBean.getResults());
                } else if (type == Constants.PULLUP_LOAD_MORE_DATA) {
                    mView.loadMore(videoBean.getResults());
                }
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });

    }


}
