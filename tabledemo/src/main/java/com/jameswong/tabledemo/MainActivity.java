package com.jameswong.tabledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_table:
                startActivity(new Intent(this, TableActivity.class));
                break;
            case R.id.btn_table_v5:
                startActivity(new Intent(this, TableV5Activity.class));
                break;
            case R.id.btn_table_bar_chart:
                startActivity(new Intent(this, TableBarChartActivity.class));
                break;
            default:
                break;
        }
    }

}
