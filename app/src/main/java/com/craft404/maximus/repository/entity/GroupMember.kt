package com.craft404.maximus.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "group_members",
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
    indices = [
        Index(
            value = ["groupId"],
            unique = false
        ),
        Index(
            value = ["contactId"],
            unique = false
        )
    ]
)
data class GroupMember(
    val groupId: Long,
    val contactId: Long
) : BaseEntity()