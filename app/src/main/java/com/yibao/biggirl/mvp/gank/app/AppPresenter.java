package com.yibao.biggirl.mvp.gank.app;

import com.yibao.biggirl.base.BaseObserver;
import com.yibao.biggirl.model.app.RemoteAppData;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.mvp.gank.app
 * @文件名: AppPresenter
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.stranger98@gmail.com
 * @创建时间: 2018/1/13 11:06
 * @描述： {TODO}
 */

public class AppPresenter implements AppContract.Presenter {
    private AppContract.View mView;
    private RemoteAppData mRemoteAppData;

    public AppPresenter(AppContract.View view) {
        this.mView = view;
        mRemoteAppData = new RemoteAppData();
        mView.setPrenter(this);
    }


    @Override
    public void start(String type, int loadType) {
        loadData(20, 1, type, loadType);
    }


    @Override
    public void loadData(int size, int page, String type, final int status) {
        mRemoteAppData.getAppData(size, page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onSuccess(List<ResultsBeanX> list) {
                        if (status == Constants.LOAD_DATA) {
                            mView.loadData(list);
                        } else if (status == Constants.REFRESH_DATA) {
                            mView.refresh(list);
                        } else if (status == Constants.LOAD_MORE_DATA) {
                            mView.onLoadMore(list);
                        }

                        mView.showNormal();
                    }

                    @Override
                    protected void onfailde(Throwable e) {
                        mView.showError();
                    }
                });
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mView = null;

    }
}
