package com.craft404.maximus.services;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.craft404.maximus.Repository;
import com.craft404.maximus.util.Constants;
import com.craft404.maximus.util.NotificationBuilder;

import java.io.File;

import kotlin.io.FilesKt;

public class MediaListenerService extends Service {

    private static final String TAG = "MediaListenerService";
    FileObserver observer;

    @Override
    public void onCreate() {
        super.onCreate();
        startWatching();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        observer.stopWatching();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startWatching() {
        final String pathToWatch = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images/";
        observer = new FileObserver(pathToWatch, FileObserver.MOVED_TO | FileObserver.DELETE) {
            @Override
            public void onEvent(int event, final String path) {
                if (event == FileObserver.MOVED_TO) {
                    Log.d(TAG, "File moved [" + pathToWatch + path + "]");
                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            File srcFile = new File(Environment.getExternalStorageDirectory(), "WhatsApp" + File.separator + "Media" + File.separator + "WhatsApp Images" + File.separator + path);
                            File destFile = new File(Constants.DELETED_MEDIA_DIR + File.separator + "temp" + File.separator + path);
                            File file = Constants.DELETED_MEDIA_DIR;
                            if (!file.exists()) file.mkdirs();
                            FilesKt.copyTo(srcFile, destFile, false, 8192);
                            Log.i(TAG, "onEvent: copied to temp");
                            scheduleJob(destFile.getAbsolutePath());
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    });
                }
                if (event == FileObserver.DELETE) {
                    Log.d(TAG, "File deleted [" + pathToWatch + "=>" + path + "]");
                    File temp = new File(Constants.DELETED_MEDIA_DIR + File.separator + "temp" + File.separator + path);
                    File file = new File(Environment.getExternalStorageDirectory(), "Maximus" + File.separator + "Whatsapp Deleted Images" + File.separator + path);
                    Log.e(TAG, "onEvent: " + path + " =>" + temp.exists() + ", file=>" + file.exists());
                    if (temp.exists()) {
                        FilesKt.copyTo(temp, file, false, 8192);
                        Log.i(TAG, "onEvent: copied to main");
                        Repository repository = new Repository(getApplication());
                        repository.updateMedia(temp.getAbsolutePath(), file.getAbsolutePath());
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        new NotificationBuilder(getApplicationContext()).showDeletedMediaNotification(bitmap);
                    }
                }
            }
        };
        observer.startWatching();
    }

    void scheduleJob(String path) {
        ComponentName serviceComponent = new ComponentName(getBaseContext(), FileDeleteService.class);
        JobInfo.Builder builder = new JobInfo.Builder((int) (Math.random() * 1000), serviceComponent);
        builder.setMinimumLatency(60 * 60 * 1000);
        builder.setOverrideDeadline(120 * 60 * 1000);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("path", path);
        builder.setExtras(bundle);
        builder.setRequiresCharging(false);
        JobScheduler jobScheduler = getBaseContext().getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
