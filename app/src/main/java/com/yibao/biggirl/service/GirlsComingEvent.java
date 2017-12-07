package com.yibao.biggirl.service;


import com.yibao.biggirl.model.girls.Girl;

import java.util.ArrayList;
import java.util.List;


public class GirlsComingEvent {

    private List<Girl> girls;

    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public GirlsComingEvent(String from, List<Girl> girls) {
        this.girls = girls;
        this.from = from;
    }

    public GirlsComingEvent(String from, Girl girl) {
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
