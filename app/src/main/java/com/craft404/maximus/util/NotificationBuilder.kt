package com.craft404.maximus.util

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.craft404.maximus.MainActivity
import com.craft404.maximus.R


const val NOTIFICATION_IMPORTANT_MESSAGE = "notification_important"
const val NOTIFICATION_ELITE = "notification_elite"
private const val GROUP_KEY_IMPORTANT = "IMPORTANT_MESSAGES"
private const val GROUP_KEY_ELITE = "ELITE"

class NotificationBuilder {
    private val context: Context
    private val notificationManager: NotificationManager
    private var from: String? = null
    private var message: String? = null
    private var messageId: Long = 0
    private val pendingIntent: PendingIntent

    constructor(context: Context) {
        this.context = context
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createImportantNotificationChannel()
        createEliteNotificationChannel()
        val notificationIntent: Intent = Intent(context, MainActivity::class.java)
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        notificationIntent.action = Intent.ACTION_MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
    }

    constructor(context: Context, from: String?, message: String?, messageId: Long) {
        this.context = context
        this.from = from
        this.message = message
        this.messageId = messageId
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent: Intent = Intent(context, MainActivity::class.java)
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        notificationIntent.action = Intent.ACTION_MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
    }

    fun showImportantNotification() {
        val notification: Notification =
            NotificationCompat.Builder(context, NOTIFICATION_IMPORTANT_MESSAGE)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.color_3))
                .setContentTitle(from)
                .setContentText(message)
                .setChannelId(NOTIFICATION_IMPORTANT_MESSAGE)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .setBigContentTitle(from)
                        .bigText(message)
                )
                .setGroup(GROUP_KEY_IMPORTANT)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        val summary: Notification =
            NotificationCompat.Builder(context, NOTIFICATION_IMPORTANT_MESSAGE)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.color_3))
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .setSummaryText("Important Messages")
                )
                .setGroup(GROUP_KEY_IMPORTANT)
                .setGroupSummary(true)
                .build()
        notificationManager.notify(messageId.toInt(), notification)
        notificationManager.notify(1, summary)
    }

    fun showEliteNotification() {
        val notification: Notification =
            NotificationCompat.Builder(context, NOTIFICATION_ELITE)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.color_4))
                .setContentTitle(from)
                .setContentText(message)
                .setGroup(GROUP_KEY_ELITE)
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_ELITE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build()
        val summary: Notification =
            NotificationCompat.Builder(context, NOTIFICATION_ELITE)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.color_4))
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .setSummaryText("Elite Gang Messages")
                )
                .setGroup(GROUP_KEY_ELITE)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(messageId.toInt(), notification)
        notificationManager.notify(3, summary)
    }

    fun createImportantNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Important"
            val description = "Important messages will be displayed here"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(NOTIFICATION_IMPORTANT_MESSAGE, name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannelGroup(
                NotificationChannelGroup(
                    GROUP_KEY_IMPORTANT,
                    "IMPORTANT MESSAGES"
                )
            )
        }
    }

    fun createEliteNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Messages from ELITE Gang Members"
            val description = "Messages from Elite Gang members will be displayed here"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_ELITE, name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannelGroup(
                NotificationChannelGroup(
                    GROUP_KEY_ELITE,
                    "MESSAGES FROM ELITE MEMBERS"
                )
            )
        }
    }

    fun cancelAll() {
        notificationManager.cancelAll()
    }

    companion object {
        private const val TAG = "NotificationBuilder"
    }
}