package com.jameswong.tabledemo.view;

import com.jameswong.tabledemo.bean.TableChart;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:12
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public interface TableV5View {
    void onLoadDataSuccess(TableChart tableChart);
    void onLoadDataFailure(String msg);
}
