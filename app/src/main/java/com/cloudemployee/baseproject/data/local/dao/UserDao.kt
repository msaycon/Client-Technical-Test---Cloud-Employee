package com.cloudemployee.baseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cloudemployee.baseproject.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY reputation DESC")
    fun observeUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE accountId = :accountId LIMIT 1")
    fun observeUser(accountId: Long): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}
