package com.jameswong.tabledemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.jameswong.tabledemo.custom.TableBarChartView

class TableBarChartActivity : AppCompatActivity() {

    lateinit var  mTbcvTableTest: TableBarChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_bar_chart)
        mTbcvTableTest = findViewById(R.id.tbcv_table_test) as TableBarChartView
        mTbcvTableTest.setData(-55f,110f,109f)

    }
}
