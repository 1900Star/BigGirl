package com.yibao.biggirl.home;

import com.yibao.biggirl.model.girls.GirlsBean;
import com.yibao.biggirl.model.girls.GrilsDataSource;
import com.yibao.biggirl.model.girls.RemoteGirlsData;
import com.yibao.biggirl.util.Constants;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
class GirlsPresenter
        implements GirlsContract.Presenter
{
    private GirlsContract.View mView;
    private RemoteGirlsData    mRemoteGirlsData;

    public GirlsPresenter(GirlsContract.View view) {
        this.mView = view;
        mRemoteGirlsData = new RemoteGirlsData();
        mView.setPrenter(this);
    }

    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {}

    @Override
    public void start() {
        loadData(20, 1, Constants.LOAD_DATA);


    }

    @Override
    public void loadData(int size, int page, int type) {
//        LogUtil.d("====== Type =====" + type);

        mRemoteGirlsData.getGirls(size, page, new GrilsDataSource.LoadGDataCallback() {
            @Override
            public void onLoadDatas(GirlsBean girlBean) {

                if (type == Constants.REFRESH_DATA) {
                    mView.refresh(girlBean.getResults());
                } else if (type == Constants.LOAD_DATA) {
                    mView.loadData(girlBean.getResults());
                } else if (type == Constants.PULLUP_LOAD_MORE_DATA) {
                    mView.loadMore(girlBean.getResults());
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
