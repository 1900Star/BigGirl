package com.yibao.biggirl.model.video;

import com.google.gson.Gson;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 06:22
 */
public class VideoBean {

    /**
     * error : false
     * results : [{"_id":"590f2780421aa90c7fefdd58","createdAt":"2017-05-07T21:56:16.410Z","desc":"【阿斗】神秘金字塔惊现世界，暗藏可怕生物！速看恐怖片《夺命金字塔》","publishedAt":"2017-05-08T11:22:01.540Z","source":"chrome","type":"休息视频","url":"http://www.bilibili.com/video/av10389462/","used":true,"who":"LHF"}]
     */

    private boolean                error;
    private List<VideoResultsBean> results;

    public static VideoBean objectFromData(String str) {

        return new Gson().fromJson(str, VideoBean.class);
    }

    public boolean isError() { return error;}

    public void setError(boolean error) { this.error = error;}

    public List<VideoResultsBean> getResults() { return results;}

    public void setResults(List<VideoResultsBean> results) { this.results = results;}
}
