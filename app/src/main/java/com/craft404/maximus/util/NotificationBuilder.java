package com.craft404.maximus.util;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.craft404.maximus.R;
import com.craft404.maximus.activity.MainActivity;


public class NotificationBuilder {
    private static final String TAG = "NotificationBuilder";
    public static final String GROUP_KEY_IMPORTANT = "IMPORTANT_MESSAGES";
    public static final String GROUP_KEY_MENTIONS = "MENTIONS";
    public static final String GROUP_KEY_ELITE = "ELITE";
    public static final String GROUP_KEY_DELETED = "DELETED";
    private final Context context;
    private final NotificationManager notificationManager;
    private String from, message;
    private long messageId;
    private final PendingIntent pendingIntent;

    public NotificationBuilder(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent;
        notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int flags = PendingIntent.FLAG_IMMUTABLE;
        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, flags);
    }

    public NotificationBuilder(Context context, String from, String message, long messageId) {
        this.context = context;
        this.from = from;
        this.message = message;
        this.messageId = messageId;
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent;
        notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int flags = PendingIntent.FLAG_IMMUTABLE;
        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, flags);
    }

    public void showImportantNotification() {

        Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.IMPORTANT_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_3))
                .setContentTitle(from)
                .setContentText(message)
                .setChannelId(context.getString(R.string.IMPORTANT_NOTIFICATION_CHANNEL))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(from)
                        .bigText(message))
                .setGroup(GROUP_KEY_IMPORTANT)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();

        Notification summary = new NotificationCompat.Builder(context, context.getString(R.string.IMPORTANT_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_3))
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Important Messages"))
                .setGroup(GROUP_KEY_IMPORTANT)
                .setGroupSummary(true)
                .build();

        notificationManager.notify((int) messageId, notification);
        notificationManager.notify(1, summary);

    }

    public void showMentionNotification() {
        Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.MENTION_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_6))
                .setContentTitle(from)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(from)
                        .bigText(message))
                .setGroup(GROUP_KEY_MENTIONS)
                .setAutoCancel(true)
                .setChannelId(context.getString(R.string.MENTION_NOTIFICATION_CHANNEL))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();

        Notification summary = new NotificationCompat.Builder(context, context.getString(R.string.MENTION_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_6))
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Mentions"))
                .setGroup(GROUP_KEY_MENTIONS)
                .setGroupSummary(true)
                .build();

        notificationManager.notify((int) messageId, notification);
        notificationManager.notify(2, summary);

    }

    public void showEliteNotification() {
        Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.ELITE_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_4))
                .setContentTitle(from)
                .setContentText(message)
                .setGroup(GROUP_KEY_ELITE)
                .setAutoCancel(true)
                .setChannelId(context.getString(R.string.ELITE_NOTIFICATION_CHANNEL))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        Notification summary = new NotificationCompat.Builder(context, context.getString(R.string.ELITE_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_4))
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Elite Gang Messages"))
                .setGroup(GROUP_KEY_ELITE)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify((int) messageId, notification);
        notificationManager.notify(3, summary);
    }

    public void showDeletedMediaNotification(Bitmap bitmap) {
        Log.e(TAG, "showDeletedMediaNotification: ");
        Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.DELETED_MEDIA_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_5))
                .setContentTitle("Media Message Recovered")
                .setChannelId(context.getString(R.string.DELETED_MEDIA_NOTIFICATION_CHANNEL))
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setGroup(GROUP_KEY_DELETED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();

        Notification summary = new NotificationCompat.Builder(context, context.getString(R.string.DELETED_MEDIA_NOTIFICATION_CHANNEL))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(context, R.color.color_5))
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Media Message Recovered"))
                .setGroup(GROUP_KEY_DELETED)
                .setGroupSummary(true)
                .build();

        notificationManager.notify((int) (Math.random() * 1000), notification);
        notificationManager.notify(4, summary);

    }

    public void createImportantNotificationChannel() {
        CharSequence name = "Important";
        String description = "Important messages will be displayed here";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(context.getString(R.string.IMPORTANT_NOTIFICATION_CHANNEL), name, importance);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_KEY_IMPORTANT, "IMPORTANT MESSAGES"));
    }

    public void createMentionNotificationChannel() {
        CharSequence name = "Mentions";
        String description = "Messages in which you are mentioned will be displayed here";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(context.getString(R.string.MENTION_NOTIFICATION_CHANNEL), name, importance);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_KEY_MENTIONS, "MENTIONS"));
    }

    public void createEliteNotificationChannel() {
        CharSequence name = "Messages from ELITE Gang Members";
        String description = "Messages from Elite Gang members will be displayed here";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(context.getString(R.string.ELITE_NOTIFICATION_CHANNEL), name, importance);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_KEY_ELITE, "MESSAGES FROM ELITE MEMBERS"));
    }

    public void createDeletedMediaNotificationChannel() {
        CharSequence name = "Deleted Media";
        String description = "You will get a notification every time a user deletes an image";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(context.getString(R.string.DELETED_MEDIA_NOTIFICATION_CHANNEL), name, importance);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_KEY_DELETED, "DELETED MEDIA"));
    }

    public void cancelAll(){
        notificationManager.cancelAll();
    }
}
