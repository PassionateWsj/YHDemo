package com.jameswong.tabledemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameswong.tabledemo.bean.Table.*;
import com.jameswong.tabledemo.bean.Table.MainData.*;
import com.jameswong.tabledemo.R;
import com.jameswong.tabledemo.listener.OnTableContentTextClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 下午0:42
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableContentAdapter extends RecyclerView.Adapter<TableContentAdapter.TableContentHolder> {
    private static final String TAG = "hjjzz";
    private int cacheViewCount = 0;

    private List<DataBean> mDataBeen;
    private OnTableContentTextClickListener clickListener;
    private long lastClickBarChartTime;
    private List<Head> mCurrentHeads;

    @Override
    public TableContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "TableContentAdapter::onCreateViewHolder");
        Log.i(TAG, "TableContentAdapter::cacheViewCount::" + cacheViewCount++);
        return new TableContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_content, parent, false));
    }

    @Override
    public void onBindViewHolder(TableContentHolder holder, final int position) {

        if (mDataBeen != null && mCurrentHeads != null) {
            holder.mTvTableContent.setText(mDataBeen.get(mCurrentHeads.get(position + 1).getDefaultIndex()).getValue());
            holder.mTvTableContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - lastClickBarChartTime < 1000) {
                        if (mDataBeen.get(mCurrentHeads.get(position+1).getDefaultIndex()).getValue().replace("%", "").matches("[-]?\\d+[.]?\\d*")) {
                            clickListener.clickContentTextPos(mCurrentHeads.get(position+1).getDefaultIndex());
                            return;
                        }
                    }
                    lastClickBarChartTime = currentTimeMillis;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeen == null ? 0 : mDataBeen.size() - 1;
    }

    public List<DataBean> getData() {
        return mDataBeen;
    }

    public void setData(List<DataBean> dataBeen) {
        if (mDataBeen == null) {
            mDataBeen = new ArrayList<>();
        }
        if (mDataBeen != dataBeen) {
            mDataBeen = dataBeen;
            notifyDataSetChanged();
        }
    }

    public void setClickListener(OnTableContentTextClickListener onTableContentTextClickListener) {
        clickListener = onTableContentTextClickListener;
        notifyDataSetChanged();
    }

    public void setHeadsData(List<Head> popHeads) {
        this.mCurrentHeads = popHeads;
        notifyDataSetChanged();
    }

    class TableContentHolder extends RecyclerView.ViewHolder {
        TextView mTvTableContent;

        public TableContentHolder(View itemView) {
            super(itemView);
            mTvTableContent = itemView.findViewById(R.id.tv_table_content);
        }
    }
}
