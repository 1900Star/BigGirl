package com.yibao.biggirl.model.app;

import com.yibao.biggirl.network.RetrofitHelper;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 */
public class RemoteAppData
        implements AppDataSource {
    @Override
    public Observable<List<ResultsBeanX>> getAppData(int size, int page, String type) {

        return RetrofitHelper.getGankApi(Constants.GANK_API)
                .getConmmentApi(type, size, page)
                .map(GankDesBean::getResults).subscribeOn(Schedulers.io());
    }


}
