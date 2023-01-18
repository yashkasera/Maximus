package com.craft404.maximus.repository

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.graphics.Color
import android.util.Log
import androidx.preference.PreferenceManager
import com.craft404.maximus.R
import com.craft404.maximus.repository.entity.Contact
import com.craft404.maximus.repository.entity.Group
import com.craft404.maximus.repository.entity.GroupMember
import com.craft404.maximus.repository.entity.Message
import com.craft404.maximus.util.AppObjectController
import com.craft404.maximus.util.NOTIFICATION_ELITE
import com.craft404.maximus.util.NOTIFICATION_IMPORTANT_MESSAGE
import com.craft404.maximus.util.NotificationBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


object Repository {
    private val groupDao by lazy { AppObjectController.database.groupDao() }
    private val messageDao by lazy { AppObjectController.database.messageDao() }
    private val contactDao by lazy { AppObjectController.database.contactDao() }
    private val groupMemberDao by lazy { AppObjectController.database.groupMemberDao() }
    private fun getNotification(contactName: String, message: String, messageId: Long) =
        NotificationBuilder(
            AppObjectController.maximusApplication,
            contactName,
            message,
            messageId
        )

    fun saveMessageToDb(from: String, message: String, groupMessage: Boolean, isImportant: Boolean) {
        if (groupMessage)
            addGroupMessage(from, message, isImportant)
        else
            addPersonalMessage(from, message, isImportant)
    }

    private fun addPersonalMessage(from: String, message: String, isImportant: Boolean) {
        val contactName: String = from.substring(from.lastIndexOf(':') + 1).trim { it <= ' ' }
        CoroutineScope(Dispatchers.IO).launch {
            val contact: Contact = contactDao.getByName(contactName) ?: Contact(
                name = contactName,
                isElite = false,
                isSpammer = false,
                icon = getColor()
            ).apply {
                this.id = contactDao.insert(this)
            }
            val messageId: Long =
                messageDao.insert(
                    Message(
                        message = message,
                        groupId = null,
                        contactId = contact.id,
                        date = Date(),
                        isImportant = isImportant,
                        isPinned = false,
                        isElite = contact.isElite,
                        isRead = false
                    )
                )
            if (contact.isElite &&
                PreferenceManager.getDefaultSharedPreferences(AppObjectController.maximusApplication)
                    .getBoolean(NOTIFICATION_ELITE, true)
            ) {
                getNotification(
                    contactName,
                    message,
                    messageId
                ).showEliteNotification()
            } else if (isImportant &&
                PreferenceManager.getDefaultSharedPreferences(AppObjectController.maximusApplication)
                    .getBoolean(NOTIFICATION_IMPORTANT_MESSAGE, true)
            )
                getNotification(
                    contactName,
                    message,
                    messageId
                ).showImportantNotification()
        }

    }

    private fun addGroupMessage(from: String, message: String, isImportant: Boolean) {
        val groupName: String = if (from.contains("(") && from.contains(" messages)")) {
            from.substring(0, from.lastIndexOf('(')).trim { it <= ' ' }
        } else {
            from.substring(0, from.lastIndexOf(':')).trim { it <= ' ' }
        }
        val contactName: String = from.substring(from.lastIndexOf(':') + 1).trim { it <= ' ' }
        CoroutineScope(Dispatchers.IO).launch {
            val groupId: Long =
                groupDao.getByName(groupName)?.let {
                    if (it.storeMessages.not()) return@launch
                    if (message == messageDao.getLastGroupMessage(it.id)?.message)
                        return@launch
                    it.unreadCount += 1
                    groupDao.update(it)
                    it.id
                } ?: run {
                    groupDao.insert(
                        Group(
                            groupName, getColor(),
                            priority = 3,
                            lastReadPosition = 0,
                            unreadCount = 0,
                            storeMessages = true,
                            emoji = groupName.first()
                        )
                    )
                }

            val contact: Contact = contactDao.getByName(contactName) ?: Contact(
                name = contactName,
                isElite = false,
                isSpammer = false,
                icon = getColor()
            ).apply {
                this.id = contactDao.insert(this)
            }
            if (groupId != -1L && contact.id != -1L) {
                if (groupMemberDao.isMember(groupId, contact.id) == null) {
                    groupMemberDao.insert(GroupMember(groupId, contact.id))
                }
                val messageId: Long =
                    messageDao.insert(
                        Message(
                            message = message,
                            groupId = groupId,
                            contactId = contact.id,
                            date = Date(),
                            isImportant = isImportant,
                            isPinned = false,
                            isElite = contact.isElite,
                            isRead = false
                        )
                    )
                if (messageId != -1L) {
                    Log.d("Repository.kt", "run: message added at :id => $messageId")
                }
                if (isImportant &&
                    PreferenceManager.getDefaultSharedPreferences(AppObjectController.maximusApplication)
                        .getBoolean(NOTIFICATION_IMPORTANT_MESSAGE, true)
                ) {
                    getNotification(
                        "$groupName : $contactName",
                        message,
                        messageId
                    ).showImportantNotification()
                }
                if (contact.isElite &&
                    PreferenceManager.getDefaultSharedPreferences(AppObjectController.maximusApplication)
                        .getBoolean(NOTIFICATION_ELITE, true)
                ) {
                    getNotification(
                        "$groupName : $contactName",
                        message,
                        messageId
                    ).showEliteNotification()
                }
            }
        }
    }

    @SuppressLint("Recycle")
    private fun getColor(): Int {
        val colors: TypedArray =
            AppObjectController.maximusApplication.resources.obtainTypedArray(R.array.loading_colors)
        val index = (Math.random() * colors.length()).toInt()
        val color = colors.getColor(index, Color.BLACK)
        Log.d("Repository.kt", "getColor() returned: $color")
        return color
    }
}