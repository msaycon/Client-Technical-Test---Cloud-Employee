package com.cloudemployee.baseproject.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cloudemployee.baseproject.data.local.dao.UserDao
import com.cloudemployee.baseproject.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
