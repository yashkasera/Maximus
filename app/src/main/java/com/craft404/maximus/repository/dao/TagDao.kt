package com.craft404.maximus.repository.dao

import androidx.room.*
import com.craft404.maximus.repository.entity.ContactTag
import com.craft404.maximus.repository.entity.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: Tag)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(tag: Tag)

    @Query("SELECT * FROM tags WHERE groupId=:groupId")
    fun getTagsByGroup(groupId: Int): Flow<List<Tag>>

    @Query("DELETE FROM tags WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(contactTag: ContactTag)

    @Query("DELETE FROM contact_tag WHERE contactId=:contactId AND tagId = :tagId")
    suspend fun removeTag(contactId: Long, tagId: Long): Int
}