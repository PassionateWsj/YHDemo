package com.jameswong.tabledemo.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jameswong.tabledemo.R;
import com.jameswong.tabledemo.bean.Table.Head;
import com.jameswong.tabledemo.bean.Table.MainData;
import com.jameswong.tabledemo.custom.TableBarChartView;
import com.jameswong.tabledemo.listener.OnHeadShowTableDataListener;
import com.jameswong.tabledemo.listener.OnTableContentTextClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 下午0:19
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableBodyAdapter extends RecyclerView.Adapter<TableBodyAdapter.TableBodyHolder> {
    private static final String TAG = "hjjzz";
    private int cacheViewCount = 0;

    private List<MainData> mMainData;

    private HashSet<RecyclerView> observerList = new HashSet<>();
    private int firstPos = -1;
    private int firstOffset = -1;
    public static int SHOW_BAR_CHAR = 2;
    public static int SHOW_NUMBER = 1;
    private int type;
    private OnHeadShowTableDataListener mOnHeadShowTableDataListener;
    private RecyclerView mRvHead;
    private int selectColumn = 0;
    private float selectColumnMinValue = 0f;
    private float selectColumnMaxValue = 0f;
    private long lastClickBarChartTime = 0;
    private RecyclerView.RecycledViewPool mPool;
    private List<Head> mCurrentHeads;

    public TableBodyAdapter(OnHeadShowTableDataListener onHeadShowTableDataListener, RecyclerView mRvHead) {
        this.mOnHeadShowTableDataListener = onHeadShowTableDataListener;
        this.mRvHead = mRvHead;
        type = SHOW_NUMBER;
        initRecyclerView(mRvHead);
    }

    @Override
    public TableBodyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TableBodyHolder tableBodyHolder = new TableBodyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_body_number, parent, false));
        initRecyclerView(tableBodyHolder.mRvTableContentText);
        Log.i(TAG, "TableBodyAdapter::onCreateViewHolder" + (observerList.size() - 1));
        Log.i(TAG, "TableBodyAdapter::cacheViewCount::" + cacheViewCount++);
        return tableBodyHolder;
    }

    @Override
    public void onBindViewHolder(final TableBodyHolder holder, int position) {
        if (mMainData != null && mCurrentHeads != null) {
            holder.mTvTableBodyKeyColumnName.setText(mMainData.get(position).getData().get(mCurrentHeads.get(0).getDefaultIndex()).getValue());
            if (type == SHOW_NUMBER) {
                holder.mRvTableContentText.setVisibility(View.VISIBLE);
                holder.mLlTableBarChart.setVisibility(View.GONE);
                holder.mTableContentAdapter.setData(mMainData.get(position).getData());
                if (mCurrentHeads != null) {
                    holder.mTableContentAdapter.setHeadsData(mCurrentHeads);
                }
                holder.mTableContentAdapter.setClickListener(new OnTableContentTextClickListener() {
                    @Override
                    public void clickContentTextPos(int pos) {
                        type = SHOW_BAR_CHAR;
                        Log.i(TAG, "点击数值:::改变UI");
                        selectColumn = pos;
                        mOnHeadShowTableDataListener.clickContentTextPos(pos);
                        notifyDataSetChanged();
                    }

                });
            } else if (type == SHOW_BAR_CHAR) {
                holder.mLlTableBarChart.setVisibility(View.VISIBLE);
                holder.mRvTableContentText.setVisibility(View.GONE);
                holder.mTvTableBarChartText.setText(mMainData.get(position).getData().get(selectColumn).getValue());
                float currentValue = Float.parseFloat(mMainData.get(position).getData().get(selectColumn).getValue().replace("%", ""));
                holder.mTbcvTableBarChart.setData(selectColumnMinValue, selectColumnMaxValue, currentValue);
                holder.mLlTableBarChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - lastClickBarChartTime < 1000) {
                            type = SHOW_NUMBER;
                            mOnHeadShowTableDataListener.showAllData();
                            notifyDataSetChanged();
                        }
                        lastClickBarChartTime = currentTimeMillis;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMainData == null ? 0 : mMainData.size();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager != null && firstPos > 0 && firstOffset > 0) {
            layoutManager.scrollToPositionWithOffset(TableBodyAdapter.this.firstPos + 1, TableBodyAdapter.this.firstOffset);
        }
        observerList.add(recyclerView);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        for (RecyclerView rv : observerList) {
                            rv.stopScroll();
                        }
                }
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstPos = linearLayoutManager.findFirstVisibleItemPosition();
                View firstVisibleItem = linearLayoutManager.getChildAt(0);
                if (firstVisibleItem != null) {
                    int firstRight = linearLayoutManager.getDecoratedRight(firstVisibleItem);
                    for (RecyclerView rv : observerList) {
                        if (recyclerView != rv) {
                            LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                            if (layoutManager != null) {
                                TableBodyAdapter.this.firstPos = firstPos;
                                TableBodyAdapter.this.firstOffset = firstRight;
                                layoutManager.scrollToPositionWithOffset(firstPos + 1, firstRight);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void setBodyData(List<MainData> main_data) {
        if (mMainData == null) {
            mMainData = new ArrayList<>();
        }
        mMainData = main_data;
        notifyDataSetChanged();
    }

    public List<MainData> getAdapterData() {
        return mMainData;
    }

    public void setBarCharMaxMin(String max, String min) {
        selectColumnMaxValue = Float.parseFloat(max.replace("%", ""));
        selectColumnMinValue = Float.parseFloat(min.replace("%", ""));
        notifyDataSetChanged();

    }

    public void setHeadData(List<Head> currentHeads) {
        this.mCurrentHeads = currentHeads;
        notifyDataSetChanged();
    }

    class TableBodyHolder extends RecyclerView.ViewHolder {
        TextView mTvTableBodyKeyColumnName;
        RecyclerView mRvTableContentText;
        TableContentAdapter mTableContentAdapter;
        TextView mTvTableBarChartText;
        TableBarChartView mTbcvTableBarChart;
        LinearLayout mLlTableBarChart;
        LinearLayoutManager mLayout;

        public TableBodyHolder(View itemView) {
            super(itemView);
            mTvTableBodyKeyColumnName = itemView.findViewById(R.id.tv_table_body_key_column_name);
            mRvTableContentText = itemView.findViewById(R.id.rv_table_content);
            mLlTableBarChart = itemView.findViewById(R.id.ll_table_bar_chart);
            mTvTableBarChartText = itemView.findViewById(R.id.tv_table_bar_chart_text);
            mTbcvTableBarChart = itemView.findViewById(R.id.tbcv_table_bar_chart);
            mLayout = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
//            mLayout.setRecycleChildrenOnDetach(true);
            mRvTableContentText.setLayoutManager(mLayout);
            if (observerList.size() == 1) {
                mPool = mRvTableContentText.getRecycledViewPool();
                mPool.setMaxRecycledViews(0, 9);
            } else if (mPool != null) {
                mRvTableContentText.setRecycledViewPool(mPool);
            }
            mTableContentAdapter = new TableContentAdapter();
            mRvTableContentText.setAdapter(mTableContentAdapter);
        }
    }
}
