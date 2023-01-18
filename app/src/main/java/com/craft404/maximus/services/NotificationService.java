package com.craft404.maximus.services;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.craft404.maximus.util.MessageReceived;

import java.util.concurrent.ExecutionException;

public class NotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";
    private static final String WHATSAPP_PACKAGE = "com.whatsapp";
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!sbn.getPackageName().equals(WHATSAPP_PACKAGE)) return;
        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;
        if (bundle.containsKey(NotificationCompat.EXTRA_TITLE) && bundle.containsKey(NotificationCompat.EXTRA_TEXT) && bundle.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)) {
            String from = bundle.getString(NotificationCompat.EXTRA_TITLE).trim();
            String message;
            bundle.get(NotificationCompat.EXTRA_TEXT).toString();
            message = bundle.get(NotificationCompat.EXTRA_TEXT).toString().trim();
            boolean isGroupMessage = bundle.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION);
            try {
                new MessageReceived(getApplication(), from, message, isGroupMessage).addMessage();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "onNotificationPosted: Invalid Message");
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.i("Msg", "Notification Removed");
    }

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
    }
}
