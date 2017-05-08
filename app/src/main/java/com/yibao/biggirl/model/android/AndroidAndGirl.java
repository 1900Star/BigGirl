package com.yibao.biggirl.model.android;

import com.yibao.biggirl.model.girls.ResultsBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${用于将 "福利" 和 "Android" 的数据组合在一起}
 * Time:2017/4/24 06:45
 */
public class AndroidAndGirl {

    public List<ResultsBeanX> mAndroidData;
    public List<ResultsBean>  mGrilData;

    public AndroidAndGirl() {}

    public AndroidAndGirl(List<ResultsBeanX> resultsBeanXes, List<ResultsBean> resultsBeen) {
        mAndroidData = resultsBeanXes;
        mGrilData = resultsBeen;
    }

    public List<ResultsBeanX> getAndroidData() {
        return mAndroidData;
    }

    public List<ResultsBean> getGrilData() {
        return mGrilData;
    }
}
