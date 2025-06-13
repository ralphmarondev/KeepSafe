package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun upsert(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getDetailById(id: Int): UserEntity?
}