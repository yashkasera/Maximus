package com.craft404.maximus.repository.dao

import androidx.room.*
import com.craft404.maximus.repository.entity.GroupMember

@Dao
interface GroupMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(groupMember: GroupMember): Long

    @Update
    fun update(vararg groupMember: GroupMember): Int

    @Query("SELECT * FROM group_members WHERE groupId = :groupId")
    fun getGroupMembers(groupId: Long): List<GroupMember>

    @Query("SELECT * FROM group_members WHERE groupId = :groupId AND contactId = :contactId")
    fun isMember(groupId: Long, contactId: Long): GroupMember?
}