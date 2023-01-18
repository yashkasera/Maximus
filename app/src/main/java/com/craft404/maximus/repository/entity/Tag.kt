package com.craft404.maximus.repository.entity

import androidx.room.Entity

@Entity(tableName = "tags")
data class Tag(
    val name: String,
    val color: Int,
    val groupId: Int
) : BaseEntity()