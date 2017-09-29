package com.jameswong.tabledemo.bean;

import java.util.List;

/**
 * Created by CANC on 2017/4/19.
 */

public class Filter {
    private String name;
    private List<FilterItem> items;
    private boolean isSelected = false;//是否打开过滤条件
    private boolean isAllcheck = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterItem> getItems() {
        return items;
    }

    public void setItems(List<FilterItem> items) {
        this.items = items;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isAllcheck() {
        return isAllcheck;
    }

    public void setAllcheck(boolean allcheck) {
        isAllcheck = allcheck;
    }
}
