package com.lab47billion.appchooser;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by prashant on 6/17/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setFontAttrId(com.lab47billion.appchooser.R.attr.fontPath)
                        .build()
        );
    }
}
