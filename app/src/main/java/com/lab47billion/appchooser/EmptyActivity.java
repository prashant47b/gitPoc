package com.lab47billion.appchooser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by prashant on 5/23/2016.
 */
public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String activity_name = checkActivityName();
        if (activity_name != null) {
//            Log.e("Package", ""+this.getCallingActivity());
            try {
                //start the activity
                Intent intent = new Intent(this, Class.forName(activity_name));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Activity found for the given name");
        }
        finish();
    }

    //check for activity name null if not present
    private String checkActivityName() {
        String activity = null;
        Intent intent = getIntent();
        if (intent.getData() != null) {
            Uri uri = getIntent().getData();
            //obtain activity name from the uri
            String componentName = uri.getLastPathSegment();
            if (componentName != null) {
                try {
                    ActivityInfo[] list = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
                    for (ActivityInfo activityInfo : list) {
                        String name = activityInfo.name;
                        //convert into lower case to avoid case sensitive issues
                        if (name.toLowerCase().endsWith(componentName.toLowerCase())) {
                            activity = name;
                            break;
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Invalid Package Name");
                }
            } else {
                System.out.println("Component Name Found null");
            }
        } else {
            System.out.println("No data found in the given intent");
        }
        return activity;
    }
}
