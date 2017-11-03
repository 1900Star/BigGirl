package com.yibao.biggirl.mvp.gank.girls;

import com.yibao.biggirl.model.girls.GrilsDataSource;
import com.yibao.biggirl.model.girls.RemoteGirlsData;
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
    private GirlsContract.View<String> mView;
    private RemoteGirlsData               mRemoteGirlsData;

    public GirlsPresenter(GirlsContract.View<String> view) {
        this.mView = view;
        mRemoteGirlsData = new RemoteGirlsData();
    }


    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {}

    @Override
    public void start(String dataType, int codeId) {
        loadData(20, 1, Constants.LOAD_DATA, dataType);

    }

    @Override
    public void loadData(int size, int page, int type, String dataType) {
        mRemoteGirlsData.getGirls(dataType, size, page, new GrilsDataSource.LoadGDataCallback() {
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
    }


}
