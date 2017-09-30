package com.jameswong.tabledemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jameswong.tabledemo.adapter.TableBodyAdapter;
import com.jameswong.tabledemo.adapter.TableHeadAdapter;
import com.jameswong.tabledemo.adapter.TableSelectColumnAdapter;
import com.jameswong.tabledemo.bean.Head;
import com.jameswong.tabledemo.bean.MainData;
import com.jameswong.tabledemo.bean.TableChart;
import com.jameswong.tabledemo.listener.OnHeadShowTableDataListener;
import com.jameswong.tabledemo.listener.OnStartDragListener;
import com.jameswong.tabledemo.listener.OnTableHeadClickListener;
import com.jameswong.tabledemo.listener.SimpleItemTouchHelperCallback;
import com.jameswong.tabledemo.presenter.TableV5Presenter;
import com.jameswong.tabledemo.view.TableV5View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.jameswong.tabledemo.R.id.tv_head_title;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/18 上午09:51
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class TableV5Activity extends AppCompatActivity implements TableV5View, OnTableHeadClickListener, OnHeadShowTableDataListener, OnStartDragListener {
    private static final String TAG = "hjjzz";

    @BindView(R.id.tv_table_name)
    TextView mTvTableName;
    @BindView(R.id.rv_head)
    RecyclerView mRvHead;
    @BindView(R.id.rv_body)
    RecyclerView mRvBody;
    @BindView(tv_head_title)
    TextView mTvHeadTitle;
    @BindView(R.id.tv_head_sort)
    TextView mTvHeadSort;
    @BindView(R.id.ll_head_container)
    LinearLayout mLlHeadContainer;
    @BindView(R.id.popup_top_reference)
    View mPopupTopReference;
    private String urlString = "http://yonghui-dev.idata.mobi/api/v1/group/165/template/5/report/90/json";
    private TableChart mTableData;
    private TableV5Presenter mPresenter;
    private TableHeadAdapter mHeadAdapter;
    private TableBodyAdapter mBodyAdapter;
    private boolean positiveSort = false;
    private int mSortPos = -1;
    private PopupWindow mPopupWindow;
    private TableSelectColumnAdapter mSelectColumnAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private List<Head> mPopHeads;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_v5);
        ButterKnife.bind(this);

        initData();
        initView();
        initAdapter();
        loadData();
    }

    private void initData() {

    }

    private void initView() {
        mRvHead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvBody.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initAdapter() {
        mHeadAdapter = new TableHeadAdapter(this, this);
        mBodyAdapter = new TableBodyAdapter(this, mRvHead);
        mRvHead.setAdapter(mHeadAdapter);
        mRvBody.setAdapter(mBodyAdapter);
    }

    private void loadData() {
        mPresenter = new TableV5Presenter(this);
        mPresenter.loadData(this, urlString);
    }

    /**
     * 加载数据成功
     *
     * @param tableChart
     */
    @Override
    public void onLoadDataSuccess(TableChart tableChart) {
        mTableData = tableChart;
        if (mTableData.getTable().getHead().size() > 0) {
            mTvTableName.setText(mTableData.getTable().getHead().get(0).getValue());
            mHeadAdapter.setHeadData(mTableData.getTable().getHead());
        }
        if (mTableData.getTable().getMain_data().size() > 0) {
            mBodyAdapter.setBodyData(mTableData.getTable().getMain_data());
        }
    }

    /**
     * 加载数据失败
     *
     * @param msg
     */
    @Override
    public void onLoadDataFailure(String msg) {

    }

    /**
     * 点击 列头 实现单列排序
     *
     * @param pos
     */
    @Override
    public void clickPos(final int pos) {
        if (mSortPos != pos && mSortPos != -1) {
            positiveSort = false;
        }
        mHeadAdapter.sortClickable(false);
        Log.i(TAG, "解析出错，非法传参");
        Observable.just(pos)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        if (positiveSort && mSortPos != pos) {
                            Collections.sort(mBodyAdapter.getAdapterData(), new Comparator<List<MainData>>() {
                                @Override
                                public int compare(List<MainData> mainDatas, List<MainData> t1) {
                                    String value = mainDatas.get(pos + 1).getValue().replace("%", "");
                                    String value1 = t1.get(pos + 1).getValue().replace("%", "");
                                    double i;
                                    i = Double.parseDouble(value) - Double.parseDouble(value1);
                                    if (i > 0) {
                                        return 1;
                                    }
                                    if (i < 0) {
                                        return -1;
                                    }
                                    return 0;
                                }
                            });
                        } else {
                            Collections.reverse(mBodyAdapter.getAdapterData());
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "排序出错:::" + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mBodyAdapter.notifyDataSetChanged();
                        mHeadAdapter.sortClickable(true);
                        mSortPos = pos;
                    }
                });
    }

    /**
     * 双击表内数据 选择单列显示条状图
     *
     * @param pos
     */
    @Override
    public void clickContentTextPos(final int pos) {
        mRvHead.setVisibility(View.GONE);
        mLlHeadContainer.setVisibility(View.VISIBLE);
        mLlHeadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPos(pos - 1);
            }
        });
        mTvHeadTitle.setText(mTableData.getTable().getHead().get(pos).getValue());
        List<List<MainData>> main_data = new ArrayList();
        main_data.addAll(mTableData.getTable().getMain_data());
        mBodyAdapter.setBarCharMaxMin(Collections.max(main_data, new Comparator<List<MainData>>() {
                    @Override
                    public int compare(List<MainData> mainDatas, List<MainData> t1) {
                        String value = mainDatas.get(pos).getValue().replace("%", "");
                        String value1 = t1.get(pos).getValue().replace("%", "");
                        double i = Double.parseDouble(value) - Double.parseDouble(value1);

                        if (i > 0) {
                            return 1;
                        }
                        if (i < 0) {
                            return -1;
                        }
                        return 0;
                    }
                }).get(pos).getValue()
                , Collections.min(main_data, new Comparator<List<MainData>>() {
                    @Override
                    public int compare(List<MainData> mainDatas, List<MainData> t1) {
                        String value = mainDatas.get(pos).getValue().replace("%", "");
                        String value1 = t1.get(pos).getValue().replace("%", "");
                        double i = Double.parseDouble(value) - Double.parseDouble(value1);
                        if (i > 0) {
                            return 1;
                        }
                        if (i < 0) {
                            return -1;
                        }
                        return 0;
                    }
                }).get(pos).getValue());
    }

    @Override
    public void showAllData() {
        mLlHeadContainer.setVisibility(View.GONE);
        mRvHead.setVisibility(View.VISIBLE);
    }

    public void clickTest(View view) {
        switch (view.getId()) {
            case R.id.tv_table_name:
                Toast.makeText(this, "选列", Toast.LENGTH_SHORT).show();
                showSelectColumn();
                break;
            default:
                break;
        }

    }

    /**
     * 选列
     */
    private void showSelectColumn() {
        View selectColumnView = LayoutInflater.from(this).inflate(R.layout.popup_table_select_column, null);

        RecyclerView mRvTableSelectColumn = selectColumnView.findViewById(R.id.rv_table_select_column);
        mRvTableSelectColumn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSelectColumnAdapter = new TableSelectColumnAdapter(mTableData.getTable().getHead(), this);
        mRvTableSelectColumn.setAdapter(mSelectColumnAdapter);

        SimpleItemTouchHelperCallback itemTouchHelperCallback = new SimpleItemTouchHelperCallback(mSelectColumnAdapter);
        mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRvTableSelectColumn);

        mPopupWindow = new PopupWindow(selectColumnView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAsDropDown(mPopupTopReference, 0, 0);

    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_column_cancel:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
                break;
            case R.id.tv_select_column_confirm:
                Toast.makeText(this, "应用", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
                mPopHeads = mSelectColumnAdapter.getPopHeads();
                mTvTableName.setText(mPopHeads.get(0).getValue());
                mHeadAdapter.setHeadData(mPopHeads);
                mBodyAdapter.setHeadData(mPopHeads);
                break;
            case R.id.btn_table_select_column_all:
                Toast.makeText(this, "全选", Toast.LENGTH_SHORT).show();
                mSelectColumnAdapter.setAllSelect(true);
                break;
            case R.id.btn_table_select_column_clear:
                Toast.makeText(this, "清空", Toast.LENGTH_SHORT).show();
                mSelectColumnAdapter.setAllSelect(false);
                break;
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
