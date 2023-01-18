package com.craft404.maximus.repository.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.craft404.maximus.repository.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message): Long

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessage(id: Long): Message?

    @Query("SELECT * FROM messages ORDER BY date DESC")
    fun getAllMessages(): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE isImportant = 1")
    fun getImportantMessages(): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE isPinned = 1")
    fun getPinnedMessages(): Flow<List<Message>>

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteMessage(id: Long)

    @Query("DELETE FROM messages WHERE isImportant = 1")
    suspend fun deleteImportantMessages()

    @Query("UPDATE messages SET isImportant = 1 WHERE id = :id")
    suspend fun markAsImportant(id: Long)

    @Query("UPDATE messages SET isImportant = 0 WHERE id = :id")
    suspend fun markAsNotImportant(id: Long)

    @Query("UPDATE messages SET isImportant = 0 WHERE isImportant = 1")
    suspend fun unmarkAllImportant()

    @Query("UPDATE messages SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE messages SET isRead = 0 WHERE id = :id")
    suspend fun markAsUnread(id: Long)

    @Query("DELETE FROM messages WHERE id in (:list)")
    suspend fun deleteMultipleMessages(list: List<Long>)

    @Query("SELECT * FROM messages WHERE groupId = :groupId ORDER BY date DESC LIMIT 1")
    suspend fun getLastGroupMessage(groupId: Long): Message?

    @Query("SELECT * FROM messages WHERE contactId = :contactId ORDER BY date DESC LIMIT 1")
    suspend fun getLastContactMessage(contactId: Long): Message?

}