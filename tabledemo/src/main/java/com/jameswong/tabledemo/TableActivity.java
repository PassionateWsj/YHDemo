package com.jameswong.tabledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jameswong.tabledemo.bean.TableChart;
import com.jameswong.tabledemo.bean.Table.*;
import com.kelin.scrollablepanel.library.ScrollablePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TableActivity extends AppCompatActivity {
    private String urlString = "http://yonghui-dev.idata.mobi/api/v1/group/165/template/5/report/90/json";
    private TableChart mOriginTableData;
    private TestPanelAdapter mTestPanelAdapter;
    private ScrollablePanel mScrollablePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        loadData();

        initAdapter();
    }

    private void initAdapter() {
        mTestPanelAdapter = new TestPanelAdapter();
        mScrollablePanel = (ScrollablePanel) findViewById(R.id.scrollable_panel);

    }

    private void loadData() {
        OKHttpUtils.newInstance().getAsyncData(urlString, new OKHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSucces(Call call, String response) {

                if (response != null) {
//                fillTableData(response);
                    Observable.just(response)
                            .subscribeOn(Schedulers.io())
                            .map(new Func1<String, TableChart>() {

                                @Override
                                public TableChart call(String s) {

                                    TableChart tableChart = new Gson().fromJson(s, TableChart.class);
                                    return fillTableData(tableChart);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<TableChart>() {
                                @Override
                                public void call(TableChart tableChart) {
                                    if (mOriginTableData != null) {
                                        Toast.makeText(TableActivity.this, "转换成功", Toast.LENGTH_SHORT).show();
                                        mTestPanelAdapter.setHeadData(tableChart.getTable().getHead());
                                        mTestPanelAdapter.setMainData(tableChart.getTable().getMain_data());
                                        mScrollablePanel.setPanelAdapter(mTestPanelAdapter);
                                    }
                                }
                            });
                }
            }
        });
    }

    public TableChart fillTableData(TableChart tableChart) {
        mOriginTableData = generateShowTableData(tableChart);

        return mOriginTableData;
    }

    /**
     * 生成 table 数据
     * 1. 是否应该展示该列
     * 2. 展示多少行
     *
     * @param tableChart
     * @return
     */
    private TableChart generateShowTableData(TableChart tableChart) {
//        List<Head> heads = new ArrayList<>();
        List<MainData> mainDatas = new ArrayList<>();
        int size = tableChart.getTable().getMain_data().size();
        int currentRowSum = 0;
        int increasingRow = 1000;
        if (size > currentRowSum) {
            currentRowSum += increasingRow;
        }
        for (int i = 0; i < currentRowSum; i++) {
            for (int j = tableChart.getTable().getMain_data().get(i).getData().size() - 1; j > 0 || j == 0; j--) {
                if (!tableChart.getTable().getHead().get(j).isShow()) {
                    tableChart.getTable().getMain_data().get(i).getData().remove(j);
                }
            }
            mainDatas.add(tableChart.getTable().getMain_data().get(i));
        }
        for (int j = tableChart.getTable().getHead().size() - 1; j > 0 || j == 0; j--) {
            if (!tableChart.getTable().getHead().get(j).isShow()) {
                tableChart.getTable().getHead().remove(j);
            }
        }
        tableChart.getTable().setMain_data(mainDatas);
        return tableChart;
    }
}
