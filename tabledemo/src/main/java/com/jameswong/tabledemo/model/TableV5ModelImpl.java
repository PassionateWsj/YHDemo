package com.jameswong.tabledemo.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jameswong.tabledemo.OKHttpUtils;
import com.jameswong.tabledemo.bean.Table;
import com.jameswong.tabledemo.bean.TableChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:14
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableV5ModelImpl implements TableV5Model {
    @Override
    public void loadData(Context context, String url, final OnTableLoadDataListener listener) {
//        loadTableData(url, listener);

        Observable.just(context)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Context, String>() {
                    @Override
                    public String call(Context context) {
                        return getJsonData(context);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        trimData(s, listener);
                    }
                });
    }

    private void loadTableData(String urlString, final OnTableLoadDataListener listener) {
        OKHttpUtils.newInstance().getAsyncData(urlString, new OKHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSucces(Call call, String response) {
                trimData(response, listener);
            }
        });
    }

    private void trimData(String response, final OnTableLoadDataListener listener) {
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
                    .subscribe(new Observer<TableChart>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.onLoadDataFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(TableChart tableChart) {
                            listener.onLoadDataSuccess(tableChart);
                        }
                    });
        }
    }

    public TableChart fillTableData(TableChart tableChart) {
        return generateShowTableData(tableChart);
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
        List<Table.MainData> mainDatas = new ArrayList<>();
        int size = tableChart.getTable().getMain_data().size();
        int currentRowSum = 0;
        int increasingRow = size;
        if (size > currentRowSum) {
            currentRowSum += increasingRow;
        }
        int k;
        int cut;
        for (int i = 0; i < increasingRow; i++) {
            for (int j = tableChart.getTable().getHead().size() - 1; j > 0 || j == 0; j--) {
                if (!tableChart.getTable().getHead().get(tableChart.getTable().getMain_data().get(i).getData().get(j).getHeadIndex()).isShow()) {
                    tableChart.getTable().getMain_data().get(i).getData().remove(j);
                }
            }
//            k = 0;
//            cut = 0;
//            for (int j = 0; j < tableChart.getTable().getMain_data().get(i).getData().size(); j++) {
//                if (!tableChart.getTable().getHead().get(k).isShow()) {
//                    tableChart.getTable().getMain_data().get(i).getData().remove(j);
//                    cut++;
//                    j--;
//                }
//                k++;
//                if (k == tableChart.getTable().getMain_data().get(i).getData().size() + cut) {
//                    break;
//                }
//            }
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

    private String getJsonData(Context context) {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = null;
        try {
//            is = getResources().getAssets().open("kpi_detaldata.json");
//            is = context.getResources().getAssets().open("temple5.json");
            is = context.getResources().getAssets().open("simple_temple5.json");
            reader = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
