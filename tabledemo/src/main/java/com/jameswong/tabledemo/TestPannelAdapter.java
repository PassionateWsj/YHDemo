package com.jameswong.tabledemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameswong.tabledemo.bean.Head;
import com.jameswong.tabledemo.bean.MainData;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/15 上午10:18
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
class TestPanelAdapter extends PanelAdapter {
    private List<List<MainData>> main_data;
    private List<Head> head;
//    private List<List<String>> data;
//    private List<List<String>> data;

    @Override
    public int getRowCount() {
        return head == null ? 0 : main_data == null ? 1 : main_data.size();
    }

    @Override
    public int getColumnCount() {
        return head == null ? 0 : head.size() - 1;
    }

    @Override
    public int getItemViewType(int row, int column) {
        return super.getItemViewType(row, column);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        String content = null;
        if (row == 0 && head != null) {
            content = head.get(column).getValue();
        } else if (main_data != null) {
            content = main_data.get(row).get(column).getValue();
        }

        ((TitleViewHolder) holder).titleTextView.setText(content);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestPanelAdapter.TitleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_title, parent, false));
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    public void setMainData(List<List<MainData>> mainData) {
        if (main_data == null) {
            main_data = new ArrayList<>();
        }
        main_data.clear();
        main_data.addAll(mainData);
    }

    public void setHeadData(List<Head> headData) {
        if (head == null) {
            head = new ArrayList<>();
        }
        head.clear();
        head.addAll(headData);
    }

}
