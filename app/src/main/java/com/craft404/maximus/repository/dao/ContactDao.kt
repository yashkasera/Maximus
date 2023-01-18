package com.craft404.maximus.repository.dao

import androidx.room.*
import com.craft404.maximus.repository.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getById(id: Long): Contact?

    @Query("SELECT * FROM contacts WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): Contact?

    @Query("SELECT * FROM contacts")
    fun getAll(): Flow<List<Contact>>

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    @Query("UPDATE contacts SET isSpammer = :isSpammer WHERE id = :id")
    suspend fun setSpammer(id: Long, isSpammer: Boolean)

    @Query("UPDATE contacts SET isElite = :isElite WHERE id = :id")
    suspend fun setElite(id: Long, isElite: Boolean)
}