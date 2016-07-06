package com.lab47billion.appchooser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;

import com.lab47billion.appchooser.R;
import com.lab47billion.appchooser.week.DateTimeInterpreter;
import com.lab47billion.appchooser.week.MonthLoader;
import com.lab47billion.appchooser.week.WeekView;
import com.lab47billion.appchooser.week.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by prashant on 6/1/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main);
        setUpWeekView();
    }

    private void setUpWeekView() {
        // Get a reference for the week view in the layout.
        final WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
        if (mWeekView != null) {
            DateTimeInterpreter dateTimeInterpreter = new DateTimeInterpreter() {
                @Override
                public String interpretDate(Calendar calendar) {
                    SimpleDateFormat weekFormat = new SimpleDateFormat("EEE-dd", Locale.getDefault());
                    return weekFormat.format(calendar.getTime());
                }

                @Override
                public String interpretTime(int i) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, 0);

                    try {
                        SimpleDateFormat sdf = DateFormat.is24HourFormat(SplashActivity.this) ? new SimpleDateFormat("HH", Locale.getDefault()) : new SimpleDateFormat("hh aa", Locale.getDefault());
                        return sdf.format(calendar.getTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "";
                    }
                }
            };
            Calendar datum = Calendar.getInstance();
            datum.add(Calendar.DATE, -12);
            mWeekView.setDateTimeInterpreter(dateTimeInterpreter);
            mWeekView.setDefaultEventColor(getResources().getColor(android.R.color.holo_green_dark));
            mWeekView.setMaxDate(Calendar.getInstance());
            mWeekView.setMinDate(datum);
            mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
                @Override
                public List<? extends WeekViewEvent> onMonthChange(int i, int i2) {
                    List<WeekViewEvent> weekViewEvents = new ArrayList<>();
                    Calendar to_time = Calendar.getInstance();
                    to_time.add(Calendar.HOUR_OF_DAY, 2);
                    weekViewEvents.add(new WeekViewEvent(124, "", Calendar.getInstance(), to_time));
                    return weekViewEvents;
                }
            });
        }
    }

}
