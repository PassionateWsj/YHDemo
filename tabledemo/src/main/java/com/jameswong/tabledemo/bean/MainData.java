package com.jameswong.tabledemo.bean;

/**
 * Created by CANC on 2017/4/19.
 */

public class MainData {
    /**
     * value : 上海
     * color : 0
     * index : 0
     */

    private String value;
    private int color;
    private int index;
    private int headIndex;

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
