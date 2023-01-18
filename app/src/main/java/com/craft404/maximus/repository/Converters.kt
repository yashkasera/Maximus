package com.craft404.maximus.repository

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun setToString(set: Set<String>): String {
        return set.joinToString(",")
    }

    @TypeConverter
    fun stringToSet(string: String): Set<String> {
        return string.split(",").toSet()
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun longToDate(long: Long): Date {
        return Date(long)
    }
}