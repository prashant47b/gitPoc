package com.lab47billion.appchooser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by prashant on 5/20/2016.
 */
public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(System.currentTimeMillis());
        setContentView(com.lab47billion.appchooser.R.layout.destination);
    }
}
