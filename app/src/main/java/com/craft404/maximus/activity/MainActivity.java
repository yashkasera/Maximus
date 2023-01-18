package com.craft404.maximus.activity;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.craft404.maximus.R;
import com.craft404.maximus.ui.dialogs.InfoDialog;
import com.craft404.maximus.util.NotificationBuilder;
import com.craft404.maximus.util.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_NOTIFICATION_ACCESS_CODE = 1002;
    private static final int PERMISSION_REQUEST_CODE = 1003;
    Toolbar toolbar;
    Context context = this;
    Snackbar snackbarNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_direct, R.id.navigation_groups, R.id.navigation_more_options)
                .build();
        checkPermissions();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
        if (sharedPreferenceManager.isFirstTimeLaunch()) {
            FragmentTransaction transaction = ((FragmentActivity) context)
                    .getSupportFragmentManager()
                    .beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("title", "New to this app?");
            bundle.putString("message", "We'd recommend you to watch this 5 minute tutorial to make the most out of this app\nhttps://youtu.be/o3De2ps6cLg\n\nYou can find this message in settings for future reference.");
            bundle.putString("button", "DISMISS");
            InfoDialog.newInstance(bundle).show(transaction, "dialog_info");
            sharedPreferenceManager.setFirstTimeLaunch(false);
        }

    }

    void checkPermissions() {
        if (!isNotificationServiceEnabled()) {
            View view = findViewById(R.id.main_activity);
            snackbarNotifications = Snackbar.make(view, "Some app features might not work. Please allow Notification access to proceed!",
                            BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("ALLOW", v -> startActivityForResult(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS), REQUEST_NOTIFICATION_ACCESS_CODE));
            snackbarNotifications.show();
        } else {
            if (snackbarNotifications != null) snackbarNotifications.dismiss();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTIFICATION_ACCESS_CODE) {
            startService(new Intent(getBaseContext(), NotificationListenerService.class));
            checkPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                startService(new Intent(getBaseContext(), NotificationListenerService.class));
                checkPermissions();
            }
        }
    }

    //Clear all notifications on app open
    @Override
    protected void onResume() {
        super.onResume();
        NotificationBuilder notificationBuilder = new NotificationBuilder(this);
        notificationBuilder.cancelAll();
    }
}