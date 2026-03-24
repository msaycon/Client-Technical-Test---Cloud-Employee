package com.cloudemployee.baseproject.di

import com.cloudemployee.baseproject.data.repository.UserRepositoryImpl
import com.cloudemployee.baseproject.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repositoryImpl: UserRepositoryImpl,
    ): UserRepository
}

