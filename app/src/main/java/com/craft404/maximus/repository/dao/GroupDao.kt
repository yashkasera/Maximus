package com.craft404.maximus.repository.dao

import androidx.room.*
import com.craft404.maximus.repository.entity.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(groups: Group): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(group: Group)

    @Query("SELECT * FROM groups")
    fun getAll(): Flow<List<Group>>

    @Query("SELECT * FROM groups WHERE id = :id")
    fun getById(id: Int): Flow<Group>

    @Query("SELECT * FROM groups WHERE name = :name")
    suspend fun getByName(name: String): Group?

    @Query("DELETE FROM groups")
    suspend fun deleteAll()

    @Query("DELETE FROM groups WHERE id = :id")
    suspend fun deleteById(id: Int)
}