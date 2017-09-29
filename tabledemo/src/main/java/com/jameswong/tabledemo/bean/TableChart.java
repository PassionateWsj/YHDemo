package com.jameswong.tabledemo.bean;

/**
 * Created by CANC on 2017/4/19.
 */

public class TableChart {
    //表名
    private String name;
    //
    private Config config;
    //数据
    private Table table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
