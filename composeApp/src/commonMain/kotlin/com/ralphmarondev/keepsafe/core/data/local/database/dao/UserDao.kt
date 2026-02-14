package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun create(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getDetailsByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE familyId = :familyId ORDER BY rank ASC")
    fun getUsersByFamilyId(familyId: String): Flow<List<UserEntity>>

    @Query("DELETE FROM users")
    suspend fun clearUsersTable()
}