package com.example.android.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.common.activities.SampleActivityBase;

import static java.lang.Thread.sleep;

public class StockSearch extends SampleActivityBase {
    String TAG = "StockSearch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] data = MainActivity.searchInfo;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);
        TextView symbol = (TextView)findViewById(R.id.symbol);
        TextView changePercent = (TextView)findViewById(R.id.changePercent);
        TextView companyName = (TextView)findViewById(R.id.companyName);
        TextView sector = (TextView)findViewById(R.id.sector);
        TextView high = (TextView)findViewById(R.id.high);
        TextView low = (TextView)findViewById(R.id.low);
        TextView latest_price = (TextView)findViewById(R.id.latest_price);
        TextView week52High = (TextView)findViewById(R.id.week52High);
        TextView week52Low = (TextView)findViewById(R.id.week52Low);
        Button back = (Button)findViewById(R.id.button);

        Log.d(TAG, "BEFORE SETS");
        try {
            symbol.setText(data[0]);
            Log.d(TAG, "set symbol");
        } catch (Exception e) {
            Log.d(TAG, "symbol is null");
        }
        try {
            changePercent.setText(data[1]);
            Log.d(TAG, "set changePercent");
        } catch (Exception e) {
            Log.d(TAG, "changePercent is null");
        }
        try {
            companyName.setText(data[2]);
            Log.d(TAG, "set companyName");
        } catch (Exception e) {
            Log.d(TAG, "companyName is null");
        }
        try {
            sector.setText(data[3]);
            Log.d(TAG, "set sector");
        } catch (Exception e) {
            Log.d(TAG, "sector is null");
        }
        try {
            high.setText(data[4]);
            Log.d(TAG, "set high");
        } catch (Exception e) {
            Log.d(TAG, "high is null");
        }
        try {
            low.setText(data[5]);
            Log.d(TAG, "set low");
        } catch (Exception e) {
            Log.d(TAG, "low is null");
        }
        try {
            latest_price.setText(data[6]);
            Log.d(TAG, "set latest_price");
        } catch (Exception e) {
            Log.d(TAG, "latest_price is null");
        }
        try {
            week52High.setText(data[7]);
            Log.d(TAG, "set week52High");
        } catch (Exception e) {
            Log.d(TAG, "week52High is null");
        }
        try {
            week52Low.setText(data[8]);
            Log.d(TAG, "set week52Low");
        } catch (Exception e) {
            Log.d(TAG, "week52Low is null");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StockSearch.this, MainActivity.class));
                finish();
            }
        });
    }

}
