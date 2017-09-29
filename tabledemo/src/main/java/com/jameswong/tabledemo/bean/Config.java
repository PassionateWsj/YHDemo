package com.jameswong.tabledemo.bean;

import java.util.List;

/**
 * Created by CANC on 2017/4/19.
 */

public class Config {
    //颜色
    private List<String> color;
    //筛选数据
    private List<Filter> filter;

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<Filter> getFilter() {
        return filter;
    }

    public void setFilter(List<Filter> filter) {
        this.filter = filter;
    }
}
