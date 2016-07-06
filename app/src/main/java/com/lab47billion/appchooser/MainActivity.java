package com.lab47billion.appchooser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lab47billion.appchooser.R.layout.activity_main);
        checkForAppUpdate(this, false);
    }

    public void Open(View view) {
        showAppChoooser();
    }

    public void OpenWallet(View view) {
//        showWalletChoooser();
        checkForAppUpdate(this, true);
    }

    private void checkForUpdates(Context context) {
        new AppUpdater(context)
                .setUpdateFrom(UpdateFrom.XML)
                .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.xml")
                .setDisplay(Display.DIALOG)
                .setDialogButtonDoNotShowAgain(null)
                .setDialogTitleWhenUpdateAvailable("Update available")
                .setDialogDescriptionWhenUpdateAvailable("Check out the latest version available of my app!")
                .setDialogButtonUpdate("Update now?")
                .setDialogTitleWhenUpdateNotAvailable("Update not available")
                .setDialogDescriptionWhenUpdateNotAvailable("No update available. Check for updates again later!")
                .showAppUpdated(true)
                .showEvery(3)
                .start();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int succesfullCheck = Integer.valueOf(sharedPreferences.getInt("prefSuccessfulChecks", 0));
        Log.e("Value", "" + succesfullCheck);
    }

    /**
     * checkForAppUpdate : checks for latest version of App available on Play store,
     * if available then show available message otherwise not available message
     *
     * @param showAppUpdatedDialogNow : if true then Dialog show instantly else based on MIN_COUNT_TO_CHECK_APP_UPDATE count.
     */
    public void checkForAppUpdate(final Context context, final boolean showAppUpdatedDialogNow) {
        Resources resource = context.getResources();
        final WeakReference<Context> contextWeakReference = new WeakReference<Context>(context);
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(context);
        appUpdaterUtils.setUpdateFrom(UpdateFrom.XML);
        appUpdaterUtils.setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.xml");
        appUpdaterUtils.withListener(new AppUpdaterUtils.UpdateListener() {
            @Override
            public void onSuccess(Update update, Boolean isUpdateAvailable) {
                if (isUpdateAvailable) {
                    Integer successfulChecks = getSuccessfulChecks();
                    if (isAbleToShow(successfulChecks, 5).booleanValue() || showAppUpdatedDialogNow) {
                        Log.e("Show", "Dialog Shown update Available");
                    }

                    setSucesfullCheck(Integer.valueOf(successfulChecks.intValue() + 1));
                } else if (showAppUpdatedDialogNow) {
                    Log.e("Show", "Update Not Available");
                }
            }

            @Override
            public void onFailed(AppUpdaterError error) {

            }
        });
        appUpdaterUtils.start();
    }

    public Boolean isAbleToShow(Integer successfulChecks, Integer showEvery) {
        return Boolean.valueOf(successfulChecks % showEvery == 0);
    }

    public Integer getSuccessfulChecks() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return Integer.valueOf(sharedPreferences.getInt("prefSuccessfulChecks", 0));
    }

    public void setSucesfullCheck(int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("prefSuccessfulChecks", value);
        editor.apply();
    }

    private void showIntentChooser() {
        Uri uri = Uri.parse("prashant://www.android.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivity(intent);
        }
    }

    private void showAppChoooser() {
        Uri uri = Uri.parse("http://gotuktuk.in:2085/destinationactivity");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Always use string resources for UI text.
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, "Share My Content");

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void showWalletChoooser() {
        Uri uri = Uri.parse("47billion://tuktukApp/checkwallet");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Always use string resources for UI text.
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, "Share My Content");

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void showBroadcastWalletChoooser() {
        Uri uri = Uri.parse("tk://gotuktuk.in:2085/CheckWallet");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Always use string resources for UI text.
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, "Share My Content");

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
