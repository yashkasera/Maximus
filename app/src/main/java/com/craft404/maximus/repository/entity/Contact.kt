package com.craft404.maximus.repository.entity

import androidx.room.Entity

@Entity(tableName = "contacts")
data class Contact(
    val name: String,
    val isElite: Boolean,
    val isSpammer: Boolean,
    val icon: Int,
) : BaseEntity()