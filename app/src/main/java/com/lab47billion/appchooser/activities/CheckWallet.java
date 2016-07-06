package com.lab47billion.appchooser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lab47billion.appchooser.R;


/**
 * Created by prashant on 5/23/2016.
 */
public class CheckWallet extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(System.currentTimeMillis());
        setContentView(R.layout.wallet_main);
    }
}
