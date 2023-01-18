package com.craft404.maximus.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "contact_tag",
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            onDelete = ForeignKey.CASCADE,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = Contact::class,
            onDelete = ForeignKey.CASCADE,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("contactId")
        ),
    ],
    indices = [
        Index(
            value = ["tagId"],
            unique = false
        ),
        Index(
            value = ["contactId"],
            unique = false
        )
    ]
)
data class ContactTag(
    val tagId: Long,
    val contactId: Long
) : BaseEntity()