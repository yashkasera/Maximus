package com.craft404.maximus.repository.entity

import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "groups")
data class Group(
    val name: String,
    val color: Int,
    var priority: Int,
    var lastReadPosition: Int,
    var unreadCount: Int = 0,
    var storeMessages: Boolean,
    val emoji: Char,
) : BaseEntity() {
    @Ignore
    var selected: Boolean = false
}