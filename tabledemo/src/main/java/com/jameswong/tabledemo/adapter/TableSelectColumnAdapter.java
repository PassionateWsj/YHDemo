package com.jameswong.tabledemo.adapter;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jameswong.tabledemo.R;
import com.jameswong.tabledemo.bean.Head;
import com.jameswong.tabledemo.listener.ItemTouchHelperAdapter;
import com.jameswong.tabledemo.listener.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/21 下午3:26
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableSelectColumnAdapter extends RecyclerView.Adapter<TableSelectColumnAdapter.ColumnViewHolder> implements ItemTouchHelperAdapter {
    private static final String TAG = "hjjzz";
    private List<Head> heads;

    private List<Head> mPopHeads;
    private OnStartDragListener mDragStartListener;

    public TableSelectColumnAdapter(List<Head> heads, OnStartDragListener dragStartListener) {
        mPopHeads = new ArrayList<>();
        mPopHeads.addAll(heads);
        this.heads = heads;
        mDragStartListener = dragStartListener;
    }

    @Override
    public ColumnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColumnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_select_column, parent, false));
    }

    @Override
    public void onBindViewHolder(final ColumnViewHolder holder, final int position) {
//        Log.i(TAG, "change line " + mPopHeads.get(position).getValue() + " pos: " + mPopHeads.get(position).getDefaultIndex() + " to " + position);
//        mPopHeads.get(position).setDefaultIndex(position);
        if (position == 0) {
            mPopHeads.get(position).setShow(true);
            holder.mIvTableSelectColumnSelect.setVisibility(View.INVISIBLE);
            holder.mTvTableSelectColumnName.setBackgroundColor(Color.parseColor("#91c941"));
            holder.mTvTableSelectColumnKeyColumn.setBackgroundColor(Color.parseColor("#91c941"));
            holder.mTvTableSelectColumnKeyColumn.setVisibility(View.VISIBLE);
            holder.mIvTableSelectColumnDrag.setVisibility(View.GONE);
        } else {
            holder.mIvTableSelectColumnSelect.setVisibility(View.VISIBLE);
            holder.mTvTableSelectColumnKeyColumn.setVisibility(View.GONE);
            holder.mIvTableSelectColumnDrag.setVisibility(View.VISIBLE);
            holder.mTvTableSelectColumnName.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.mTvTableSelectColumnKeyColumn.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.mTvTableSelectColumnName.setText(mPopHeads.get(position).getValue());
        if (mPopHeads.get(position).isShow()) {
            holder.mIvTableSelectColumnSelect.setImageResource(R.drawable.checkbox_selected);
        } else {
            holder.mIvTableSelectColumnSelect.setImageResource(R.drawable.checkbox_unselected);
        }
        holder.mLlTableSelectColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    return;
                }
                mPopHeads.get(position).setShow(!mPopHeads.get(position).isShow());
//                notifyItemChanged(position);
                notifyItemChanged(position);
                Log.i(TAG, "点击:::" + position);
            }
        });
        // Start a drag whenever the handle view it touched
        holder.mIvTableSelectColumnDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPopHeads == null ? 0 : mPopHeads.size();
    }

    public void setAllSelect(boolean isAllSelect) {
        for (Head head : mPopHeads) {
            head.setShow(isAllSelect);
        }
        notifyDataSetChanged();
    }

    public List<Head> getPopHeads() {
        return mPopHeads;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mPopHeads, fromPosition, toPosition);
//        mPopHeads.get(fromPosition).setDefaultIndex(toPosition);
//        mPopHeads.get(toPosition).setDefaultIndex(fromPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mPopHeads.get(position).setShow(!mPopHeads.get(position).isShow());
        notifyItemChanged(position);
//        mPopHeads.remove(position);
//        notifyItemRemoved(position);
    }

    class ColumnViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_table_select_column_select)
        ImageView mIvTableSelectColumnSelect;
        @BindView(R.id.tv_table_select_column_name)
        TextView mTvTableSelectColumnName;
        @BindView(R.id.tv_table_select_column_keyColumn)
        TextView mTvTableSelectColumnKeyColumn;
        @BindView(R.id.ll_table_select_column)
        LinearLayout mLlTableSelectColumn;
        @BindView(R.id.iv_table_select_column_drag)
        ImageView mIvTableSelectColumnDrag;

        public ColumnViewHolder(View itemView) {
            super(itemView);
//            mIvTableSelectColumnSelect=  itemView.findViewById(R.id.iv_table_select_column_select);
//            mTvTableSelectColumnName=   itemView.findViewById(R.id.tv_table_select_column_name);
//            mTvTableSelectColumnKeyColumn= itemView.findViewById(R.id.tv_table_select_column_keyColumn);
//            mLlTableSelectColumn=itemView.findViewById(R.id.ll_table_select_column);
            ButterKnife.bind(this, itemView);
        }
    }

}
