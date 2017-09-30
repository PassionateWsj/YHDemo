package com.jameswong.tabledemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jameswong.tabledemo.R;
import com.jameswong.tabledemo.bean.Table.*;
import com.jameswong.tabledemo.listener.OnTableHeadClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午10:27
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableHeadAdapter extends RecyclerView.Adapter<TableHeadAdapter.HeadViewHolder> {
    private static final String TAG = "hjjzz";
    private int cacheViewCount = 0;

    private Context mContext;
    private List<Head> mHeads;
    private OnTableHeadClickListener mOnTableHeadClickListener;
    private boolean isSortClickable;

    public TableHeadAdapter(Context mContext, OnTableHeadClickListener onTableHeadClickListener) {
        this.mContext = mContext;
        mOnTableHeadClickListener = onTableHeadClickListener;
        isSortClickable = true;
    }

    @Override
    public HeadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "TableHeadAdapter::onCreateViewHolder");
        Log.i(TAG, "TableBodyAdapter::cacheViewCount::" + cacheViewCount++);
        return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_head, parent, false));
    }

    @Override
    public void onBindViewHolder(final HeadViewHolder holder, final int position) {
        holder.mLlHeadContainerLayoutParams.width = (int) (80 * mContext.getResources().getDisplayMetrics().density + 0.5f);
        holder.mTvHeadTitle.setText(mHeads.get(position).getValue());
        holder.mTvHeadTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSortClickable && mHeads.get(position).getDefaultIndex() != 0) {
                    mOnTableHeadClickListener.clickPos(mHeads.get(position).getDefaultIndex());
                } else {
                    Toast.makeText(mContext, "点太快了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeads == null ? 0 : mHeads.size();
    }

    public void setHeadData(List<Head> head) {
        if (mHeads == null) {
            mHeads = new ArrayList<>();
        }
        mHeads.clear();
        mHeads.addAll(head);
        if (mHeads.size() > 1) {
            mHeads.remove(0);
        }
        notifyDataSetChanged();
    }

    public List<Head> getAdapterData() {
        return mHeads;
    }

    public void sortClickable(boolean isSortClickable) {
        this.isSortClickable = isSortClickable;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView mTvHeadTitle;
        LinearLayout mLlHeadContainer;
        ViewGroup.LayoutParams mLlHeadContainerLayoutParams;

        public HeadViewHolder(View itemView) {
            super(itemView);
            mLlHeadContainer = itemView.findViewById(R.id.ll_head_container);
            mTvHeadTitle = itemView.findViewById(R.id.tv_head_title);
            mLlHeadContainerLayoutParams = mLlHeadContainer.getLayoutParams();
        }
    }
}
