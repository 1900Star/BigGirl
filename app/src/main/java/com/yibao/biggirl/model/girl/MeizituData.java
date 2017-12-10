package com.yibao.biggirl.model.girl;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.model.girl
 *  @文件名:   MeizituData
 *  @创建者:   Stran
 *  @创建时间:  2017/12/10 14:50
 *  @描述：    TODO
 */

import com.yibao.biggirl.model.girls.Girl;

import java.util.ArrayList;
import java.util.List;

public class MeizituData {
    private List<Girl> girls;

    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public MeizituData(String from, List<Girl> girls) {
        this.girls = girls;
        this.from = from;
    }

    public MeizituData(String from, Girl girl) {
        this.girls = new ArrayList<>();
        girls.add(girl);
        this.from = from;
    }

    public List<Girl> getGirls() {
        return girls;
    }

    public void setGirls(List<Girl> girls) {
        this.girls = girls;
    }
}
