package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun upsert(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE uid = :uid AND isDeleted = 0 LIMIT 1")
    suspend fun getDetailByUId(uid: String): UserEntity?

    @Query("SELECT * FROM users WHERE isDeleted = 0")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}