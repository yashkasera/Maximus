package com.craft404.maximus.util

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import com.craft404.maximus.repository.Repository
import java.util.concurrent.ExecutionException

class NotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Log.d("NotificationListener.kt", "YASH => onNotificationPosted:24 ${sbn?.packageName}")
        if (sbn?.packageName != WHATSAPP_PACKAGE) return
        val notification = sbn.notification
        val bundle = notification.extras
        if (bundle.containsKey(NotificationCompat.EXTRA_TITLE) && bundle.containsKey(
                NotificationCompat.EXTRA_TEXT
            ) && bundle.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)
        ) {
            val from = bundle.getString(NotificationCompat.EXTRA_TITLE)!!.trim { it <= ' ' }
            val message = bundle[NotificationCompat.EXTRA_TEXT].toString().trim { it <= ' ' }
            val isGroupMessage = bundle.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)
            try {
                Log.d("NotificationListener.kt", "YASH => onNotificationPosted:37 $from $message")
                val isImportant = MessageUtils(from, message).isImportantMessage()
                Repository.saveMessageToDb(from, message, isGroupMessage, isImportant)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Log.d("NotificationListener.kt", "YASH => onNotificationPosted:43 Invalid Message")
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("NotificationListener.kt", "YASH => onListenerConnected:49 ")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d("NotificationListener.kt", "YASH => onListenerDisconnected:54 ")
    }
}