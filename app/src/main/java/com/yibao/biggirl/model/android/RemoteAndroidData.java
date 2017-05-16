package com.yibao.biggirl.model.android;

import com.yibao.biggirl.model.girls.GirlsBean;
import com.yibao.biggirl.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 */
public class RemoteAndroidData
        implements AndroidDataSource
{
    private List<AndroidAndGirl> mList;

    @Override
    public void getAndroid(int size, int page, LoadADataCallback callback) {
        Observable.zip(RetrofitHelper.getGankApi()
                                     .getAndroid("Android", size, page),
                       RetrofitHelper.getGankApi()
                                     .getGril("福利", size, page),
                       this::zipHelper)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new AndroidObserver(callback));




    }


    private class AndroidObserver
            implements Observer<AndroidAndGirl>
    {


        private LoadADataCallback mCallback;

        private AndroidObserver(LoadADataCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(AndroidAndGirl bean) {
            mList = new ArrayList<>();
            for (int i = 0; i < bean.mGrilData.size(); i++) {
                AndroidAndGirl itemData = new AndroidAndGirl(bean.mAndroidData,
                                                             bean.mGrilData);
                mList.add(itemData);
            }
            mCallback.onLoadData(mList);

            //将数据发到
//            EventBus.getDefault()
//                    .post(mList);


        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private AndroidAndGirl zipHelper(AndroidDesBean androidDesBean, GirlsBean girlBean) {
        AndroidAndGirl item = new AndroidAndGirl();
        for (int i = 0; i < girlBean.getResults()
                                    .size(); i++) {

            item.mAndroidData = androidDesBean.getResults();
            item.mGrilData = girlBean.getResults();

        }


        return item;
    }

}
