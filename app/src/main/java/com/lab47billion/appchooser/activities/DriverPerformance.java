package com.lab47billion.appchooser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.lab47billion.appchooser.R;
import com.lab47billion.appchooser.widget.CenterLockDateAdapter;
import com.lab47billion.appchooser.widget.CenterLockListener;
import com.lab47billion.appchooser.widget.CenterLockRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prashant on 6/10/2016.
 */
public class DriverPerformance extends AppCompatActivity {
    private CenterLockRecyclerView recyclerView;
    private TextView data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_performance);
        recyclerView = (CenterLockRecyclerView) findViewById(R.id.recyclerView);
        data = (TextView) findViewById(R.id.data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        final CenterLockDateAdapter performanceAdapter = new CenterLockDateAdapter(this,2);
        recyclerView.setAdapter(performanceAdapter);
        recyclerView.scrollToPosition(0);
        recyclerView.setCenterLockListener(new CenterLockListener() {
            @Override
            public void onCenterLock(int position) {
                if (performanceAdapter.getDate(position) != null) {
                    Date date=performanceAdapter.getDate(position).getTime();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM yyyy");
                    data.setText(simpleDateFormat.format(date));
                }
            }
        });
    }


}
