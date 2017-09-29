package com.jameswong.tabledemo.presenter;

import android.content.Context;

import com.jameswong.tabledemo.bean.TableChart;
import com.jameswong.tabledemo.model.OnTableLoadDataListener;
import com.jameswong.tabledemo.model.TableV5ModelImpl;
import com.jameswong.tabledemo.view.TableV5View;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:13
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableV5Presenter {
    private TableV5View mTableV5View;
    private TableV5ModelImpl mModel;

    public TableV5Presenter(TableV5View tableV5View) {
        mTableV5View = tableV5View;
        mModel = new TableV5ModelImpl();
    }

    public void loadData(Context context,String url) {
        mModel.loadData(context,url, new OnTableLoadDataListener() {
            @Override
            public void onLoadDataSuccess(TableChart tableChart) {
                mTableV5View.onLoadDataSuccess(tableChart);
            }

            @Override
            public void onLoadDataFailure(String msg) {
                mTableV5View.onLoadDataFailure(msg);
            }
        });
    }
}
