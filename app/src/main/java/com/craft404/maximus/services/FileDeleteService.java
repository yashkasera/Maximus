package com.craft404.maximus.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;
import android.util.Log;

import java.io.File;

public class FileDeleteService extends JobService {

    private static final String TAG = "FileDeleteService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: Job Started");
        PersistableBundle bundle = params.getExtras();
        String path = bundle.getString("path");
        Log.i(TAG, "doInBackground: PATH=>" + path);
        File file = new File(path);
        if (file.exists()) {
            Log.i(TAG, "doInBackground: file exists");
            if (file.delete()) {
                Log.i(TAG, "run: file deleted");
                jobFinished(params, false);
            } else {
                Log.i(TAG, "run: File deletion failed! PATH=>" + path);
                jobFinished(params, true);
            }
        } else {
            Log.i(TAG, "doInBackground: File does not exist. PATH = >" + path);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: Job Cancelled before cancellation");
        return true;
    }
}
