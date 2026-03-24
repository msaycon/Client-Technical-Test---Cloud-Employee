package com.cloudemployee.baseproject.di

import android.content.Context
import androidx.room.Room
import com.cloudemployee.baseproject.core.database.AppDatabase
import com.cloudemployee.baseproject.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "cloud_employee.db",
    ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}
