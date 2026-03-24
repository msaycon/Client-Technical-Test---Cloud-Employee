package com.cloudemployee.baseproject.data.repository

import com.cloudemployee.baseproject.core.common.DispatchersProvider
import com.cloudemployee.baseproject.core.network.NetworkConstants
import com.cloudemployee.baseproject.data.local.dao.UserDao
import com.cloudemployee.baseproject.data.mapper.toDomain
import com.cloudemployee.baseproject.data.mapper.toEntity
import com.cloudemployee.baseproject.data.remote.api.UserApiService
import com.cloudemployee.baseproject.domain.model.User
import com.cloudemployee.baseproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao,
    private val dispatchersProvider: DispatchersProvider,
) : UserRepository {

    override fun observeUsers(): Flow<List<User>> {
        return userDao.observeUsers().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeUser(accountId: Long): Flow<User?> {
        return userDao.observeUser(accountId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun refreshUsers(inName: String): Result<Unit> = withContext(dispatchersProvider.io) {
        runCatching {
            val remoteUsers = userApiService.getUsers(
                order = NetworkConstants.ORDER,
                sort = NetworkConstants.SORT,
                filter = NetworkConstants.FILTER,
                site = NetworkConstants.SITE,
                key = NetworkConstants.API_KEY,
                pageSize = NetworkConstants.PAGE_SIZE,
                inName = inName,
            )
            userDao.clearUsers()
            userDao.insertUsers(remoteUsers.items.map { dto -> dto.toEntity() })
        }
    }
}
