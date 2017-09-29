package com.jameswong.tabledemo.bean;

import java.util.List;

/**
 * Created by CANC on 2017/4/19.
 */

public class Table {
    private List<Head> head;
    private List<List<MainData>> main_data;

    public List<Head> getHead() {
        return head;
    }

    public void setHead(List<Head> head) {
        this.head = head;
    }

    public List<List<MainData>> getMain_data() {
        return main_data;
    }

    public void setMain_data(List<List<MainData>> main_data) {
        this.main_data = main_data;
    }
}
