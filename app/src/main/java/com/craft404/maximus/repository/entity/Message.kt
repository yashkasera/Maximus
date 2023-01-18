package com.craft404.maximus.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = Group::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("groupId")
    ), ForeignKey(
        entity = Contact::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("contactId")
    )],
    indices = [Index(
        value = ["groupId"],
        unique = false
    ), Index(
        value = ["contactId"],
        unique = false
    )]
)
data class Message(
    val message: String,
    val groupId: Long? = null,
    val contactId: Long,
    val date: Date,
    val isImportant: Boolean = false,
    val isPinned: Boolean,
    val isElite: Boolean,
    val isRead: Boolean = false,
) : BaseEntity()