package com.craft404.maximus.repository.entity

import androidx.room.PrimaryKey

open class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}