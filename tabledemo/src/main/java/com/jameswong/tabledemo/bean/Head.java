package com.jameswong.tabledemo.bean;

/**
 * Created by CANC on 2017/4/20.
 */

public class Head {

    /**
     * value : 销量
     */
    private int defaultIndex;

    public int getDefaultIndex() {
        return defaultIndex;
    }

    public void setDefaultIndex(int defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    private String value;
    private boolean show;
    private int originPosition;
    private boolean isKeyColumn;//是否为关键列
    private String sort = "default";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getOriginPosition() {
        return originPosition;
    }

    public void setOriginPosition(int originPosition) {
        this.originPosition = originPosition;
    }

    public boolean isKeyColumn() {
        return isKeyColumn;
    }

    public void setKeyColumn(boolean keyColumn) {
        isKeyColumn = keyColumn;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
