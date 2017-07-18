package com.yibao.biggirl.mvp.girls;

import com.yibao.biggirl.model.all.RemoteAllData;
import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.model.girls.GrilsDataSource;
import com.yibao.biggirl.model.girls.RemoteGirlsData;
import com.yibao.biggirl.model.video.RemoteVideoData;
import com.yibao.biggirl.model.video.VideoDataSource;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class GirlsPresenter
        implements GirlsContract.Presenter
{
    private       GirlsContract.View mView;
    private       RemoteGirlsData    mRemoteGirlsData;
    private       RemoteVideoData    mRemoteVideoData;
    private final RemoteAllData      mRemoteAllData;

    public GirlsPresenter(GirlsContract.View view) {
        this.mView = view;
        mRemoteGirlsData = new RemoteGirlsData();
        mRemoteVideoData = new RemoteVideoData();
        mRemoteAllData = new RemoteAllData();
    }


    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {}

    @Override
    public void start(String dataType, int codeId) {
        loadData(20, 1, Constants.LOAD_DATA, codeId, dataType);

    }

    @Override
    public void loadData(int size, int page, int codeId, int type, String dataType) {
        switch (dataType) {
            case Constants.FRAGMENT_GIRLS:

                mRemoteGirlsData.getGirls(dataType,
                                          size,
                                          page,
                                          new GrilsDataSource.LoadGDataCallback() {
                                              @Override
                                              public void onLoadDatas(List<String> girlBean) {

                                                  if (type == Constants.REFRESH_DATA) {
                                                      mView.refresh(girlBean);
                                                  } else if (type == Constants.LOAD_DATA) {
                                                      mView.loadData(girlBean);
                                                  } else if (type == Constants.LOAD_MORE_DATA) {
                                                      mView.loadMore(girlBean);
                                                  }
                                                  mView.showNormal();
                                              }

                                              @Override
                                              public void onDataNotAvailable() {
                                                  mView.showError();
                                              }
                                          });
                break;
            case Constants.FRAGMENT_VIDEO:
//                switch (codeId) {
//
//                    case 1:
//                        dataType = Constants.FRAGMENT_ANDROID;
//                        break;
//                    case 2:
//
//                        dataType = Constants.FRAGMENT_VIDEO;
//                        break;
//
//                }
                mRemoteVideoData.getVideo(size,
                                          page,
                                          dataType,
                                          new VideoDataSource.LoadVDataCallback() {
                                              @Override
                                              public void onLoadDatas(AndroidDesBean videoBean) {
                                                  if (type == Constants.LOAD_DATA) {
                                                      mView.loadData(videoBean.getResults());
                                                  } else if (type == Constants.REFRESH_DATA) {
                                                      mView.refresh(videoBean.getResults());
                                                  } else if (type == Constants.LOAD_MORE_DATA) {
                                                      mView.loadMore(videoBean.getResults());
                                                  }
                                              }

                                              @Override
                                              public void onDataNotAvailable() {
                                                  mView.showError();
                                              }
                                          });
                break;
        }


    }


}
