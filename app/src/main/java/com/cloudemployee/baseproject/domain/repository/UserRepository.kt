package com.cloudemployee.baseproject.domain.repository

import com.cloudemployee.baseproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUsers(): Flow<List<User>>
    fun observeUser(accountId: Long): Flow<User?>
    suspend fun refreshUsers(inName: String): Result<Unit>
}
