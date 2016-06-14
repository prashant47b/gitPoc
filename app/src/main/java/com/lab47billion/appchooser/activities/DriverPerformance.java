package com.lab47billion.appchooser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.lab47billion.appchooser.HorizontalCalenderAdapter;
import com.lab47billion.appchooser.R;
import com.lab47billion.appchooser.widget.CenterLockListener;
import com.lab47billion.appchooser.widget.HorizontalCalender;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prashant on 6/10/2016.
 */
public class DriverPerformance extends AppCompatActivity {
    private HorizontalCalender recyclerView;
    private TextView data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_performance);
        recyclerView = (HorizontalCalender) findViewById(R.id.recyclerView);
        data = (TextView) findViewById(R.id.data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        int mWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        HorizontalCalenderAdapter performanceAdapter = new HorizontalCalenderAdapter(this, mWidth);
        recyclerView.setAdapter(performanceAdapter);
        recyclerView.scrollToPosition(0);
        recyclerView.setCenterLockListener(new CenterLockListener() {
            @Override
            public void onCenterLock(int position, Calendar calendar) {
                if (calendar != null) {
                    Date date=calendar.getTime();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM yyyy");
                    data.setText(simpleDateFormat.format(date));
                }
            }
        });
    }


}
