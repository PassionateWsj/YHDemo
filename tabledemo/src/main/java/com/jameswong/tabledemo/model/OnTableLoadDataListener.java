package com.jameswong.tabledemo.model;

import com.jameswong.tabledemo.bean.TableChart;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:15
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public interface OnTableLoadDataListener {
    void onLoadDataSuccess(TableChart tableChart);
    void onLoadDataFailure(String msg);
}
