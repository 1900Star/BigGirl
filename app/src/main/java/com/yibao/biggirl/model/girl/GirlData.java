package com.yibao.biggirl.model.girl;

import com.yibao.biggirl.model.girls.ResultsBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/29 11:23
 */
public class GirlData {
    private List<ResultsBean> mList;

  private int position;

    public GirlData(int position,List<ResultsBean> list ) {
        mList = list;
        this.position = position;
    }

    public List<ResultsBean> getList() {
        return mList;
    }

    public int getPosition() {
        return position;
    }
}