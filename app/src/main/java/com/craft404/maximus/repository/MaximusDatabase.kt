package com.craft404.maximus.repository

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.craft404.maximus.repository.dao.*
import com.craft404.maximus.repository.entity.*

@Database(
    entities = [
        Contact::class,
        Group::class,
        GroupMember::class,
        Message::class,
        ContactTag::class,
        Tag::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MaximusDatabase : RoomDatabase(){
    abstract fun contactDao(): ContactDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMemberDao(): GroupMemberDao
    abstract fun messageDao(): MessageDao
    abstract fun tagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: MaximusDatabase? = null

        fun getInstance(context: Context): MaximusDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MaximusDatabase::class.java, "MaximusDatabase.db"
            ).build()
    }
}