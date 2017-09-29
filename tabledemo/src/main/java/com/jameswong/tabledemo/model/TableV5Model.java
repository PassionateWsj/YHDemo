package com.jameswong.tabledemo.model;

import android.content.Context;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:15
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
interface TableV5Model {
    void loadData(Context context, String url, OnTableLoadDataListener listener);
}
