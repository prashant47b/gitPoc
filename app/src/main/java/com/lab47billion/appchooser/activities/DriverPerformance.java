package com.lab47billion.appchooser.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab47billion.appchooser.R;
import com.lab47billion.appchooser.adapter.DriverRideListAdapter;
import com.lab47billion.appchooser.widget.CenterLockDateAdapter;
import com.lab47billion.appchooser.widget.CenterLockListener;
import com.lab47billion.appchooser.widget.CenterLockRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by prashant on 6/10/2016.
 */
public class DriverPerformance extends AppCompatActivity {
    private CenterLockRecyclerView recyclerView;
    private RecyclerView driverRidesList;
    private TextView earningTextView,earningAmountTextView,ridesTextView,ridesCount,cancelledTextView,cancelledCount,
            leaderboardTextView;
    private ImageView earningImageView;
    private RelativeLayout shuffleRelativeLayout;
    private TextView firstTextView,secondTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_performance);
        idInit();
        setupCenteredRecyclerView();
        setupDriverRidesRecyclerView();
        setShuffleView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void idInit(){
        recyclerView = (CenterLockRecyclerView) findViewById(R.id.recyclerView);
        driverRidesList= (RecyclerView) findViewById(R.id.driverRidesList);
        earningTextView= (TextView) findViewById(R.id.earningTextView);
        earningAmountTextView= (TextView) findViewById(R.id.earningAmountTextView);
        ridesTextView= (TextView) findViewById(R.id.ridesTextView);
        ridesCount= (TextView) findViewById(R.id.ridesCount);
        cancelledTextView= (TextView) findViewById(R.id.cancelledTextView);
        cancelledCount= (TextView) findViewById(R.id.cancelledCount);
        leaderboardTextView= (TextView) findViewById(R.id.leaderboardTextView);
        earningImageView= (ImageView) findViewById(R.id.earningImageView);

        shuffleRelativeLayout = (RelativeLayout) findViewById(R.id.shuffleRelativeLayout);
        firstTextView = (TextView) findViewById(R.id.firstTextView);
        secondTextView = (TextView) findViewById(R.id.secondTextView);
    }

    private void setupCenteredRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
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
//                    data.setText(simpleDateFormat.format(date));
                    if (date.after(new Date())) {
                        earningImageView.setActivated(true);
                    }else {
                        earningImageView.setActivated(false);
                    }
                }
            }
        });
    }

    private void setShuffleView(){
        firstTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleRelativeLayout.removeAllViews();
                shuffleRelativeLayout.addView(secondTextView);
                shuffleRelativeLayout.addView(firstTextView);
                shuffleRelativeLayout.invalidate();
            }
        });

        secondTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleRelativeLayout.removeAllViews();
                shuffleRelativeLayout.addView(firstTextView);
                shuffleRelativeLayout.addView(secondTextView);
                shuffleRelativeLayout.invalidate();
            }
        });
    }

    private void setupDriverRidesRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        driverRidesList.setLayoutManager(linearLayoutManager);
        List list=new ArrayList();
        for (int i=0;i<20;i++){
            list.add("data");
        }
        DriverRideListAdapter performanceAdapter = new DriverRideListAdapter(this,list);
        driverRidesList.setAdapter(performanceAdapter);
    }
}
