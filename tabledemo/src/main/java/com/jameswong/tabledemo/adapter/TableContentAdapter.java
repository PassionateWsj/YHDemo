package com.jameswong.tabledemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameswong.tabledemo.R;
import com.jameswong.tabledemo.bean.Head;
import com.jameswong.tabledemo.bean.MainData;
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

    private List<MainData> mMainData;
    private OnTableContentTextClickListener clickListener;
    private long lastClickBarChartTime;
    private List<Head> mPopHeads;
    private int selectColumn = -1;

    @Override
    public TableContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "TableContentAdapter::onCreateViewHolder");
        Log.i(TAG, "TableContentAdapter::cacheViewCount::" + cacheViewCount++);
        return new TableContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_content, parent, false));
    }

    @Override
    public void onBindViewHolder(TableContentHolder holder, final int position) {

        holder.mTvTableContent.setText(mMainData.get(getRealMainDataPos(position)).getValue());
        holder.mTvTableContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - lastClickBarChartTime < 1000) {
                    int realPos = getRealMainDataPos(position);
                    if (mMainData.get(realPos).getValue().replace("%", "").matches("[-]?\\d+[.]?\\d*")) {
                        clickListener.clickContentTextPos(realPos);
                        return;
                    }
                }
                lastClickBarChartTime = currentTimeMillis;
            }
        });
    }

    private int getRealMainDataPos(int position) {
        if (mPopHeads != null) {
            for (int i = 0; i < mMainData.size(); i++) {
                if (mMainData.get(i).getHeadIndex() == mPopHeads.get(position + 1).getDefaultIndex()) {
                    return i;
                }
            }
        }
        return position + 1;

    }

    @Override
    public int getItemCount() {
        return mMainData == null ? 0 : mMainData.size() - 1;
    }

    public List<MainData> getData() {
        return mMainData;
    }

    public void setData(List<MainData> main_data) {
        if (mMainData == null) {
            mMainData = new ArrayList<>();
        }
        if (mMainData != main_data) {
            mMainData = main_data;
            notifyDataSetChanged();
        }
    }

    public void setClickListener(OnTableContentTextClickListener onTableContentTextClickListener) {
        clickListener = onTableContentTextClickListener;
        notifyDataSetChanged();
    }

    public void setHeadsData(List<Head> popHeads) {
        this.mPopHeads = popHeads;
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
