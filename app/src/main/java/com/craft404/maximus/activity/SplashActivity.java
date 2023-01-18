package com.craft404.maximus.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.craft404.maximus.R;
import com.craft404.maximus.services.MediaListenerService;
import com.craft404.maximus.util.NotificationBuilder;
import com.craft404.maximus.util.SharedPreferenceManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    SharedPreferences sharedPreferences;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new NotificationBuilder(this).createImportantNotificationChannel();
        new NotificationBuilder(this).createEliteNotificationChannel();
        new NotificationBuilder(this).createMentionNotificationChannel();
        new NotificationBuilder(this).createDeletedMediaNotificationChannel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissionOrStart();
    }

    void checkPermissionOrStart() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(getBaseContext(), MediaListenerService.class));
                if (sharedPreferenceManager.isFirstTimeLaunch()) {
                    startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                    finish();
                    return;
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
//                    else{
//                    new AlertDialog.Builder(SplashActivity.this, R.style.AlertDialogCustom)
//                            .setTitle("Enable Notification Access")
//                            .setMessage("Please allow " + getString(R.string.app_name) + " to read notifications.\nThese messages are stored locally and are not accessible by us")
//                            .setCancelable(false)
//                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
//                                }
//                            }).show();
//                }
//            }
            }
        }, 1000);
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}