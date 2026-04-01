package com.cloudemployee.baseproject.data.repository

import com.cloudemployee.baseproject.core.common.DispatchersProvider
import com.cloudemployee.baseproject.data.local.dao.UserDao
import com.cloudemployee.baseproject.data.local.entity.UserEntity
import com.cloudemployee.baseproject.data.remote.api.UserApiService
import com.cloudemployee.baseproject.data.remote.dto.User
import com.cloudemployee.baseproject.data.remote.dto.UsersResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val userApiService = mock<UserApiService>()
    private val userDao = mock<UserDao>()
    private val dispatchersProvider = mock<DispatchersProvider>()
    private val testDispatcher = StandardTestDispatcher()

    private val repository = UserRepositoryImpl(
        userApiService = userApiService,
        userDao = userDao,
        dispatchersProvider = dispatchersProvider,
    )

    @Test
    fun `observeUsers maps dao entities into domain models`() = runTest {
        whenever(userDao.observeUsers()).thenReturn(
            flowOf(
            listOf(
                UserEntity(
                    accountId = 1L,
                    reputation = 1500,
                    creationDate = 123L,
                    profileImage = "image",
                    displayName = "Kevin",
                ),
            ),
            ),
        )

        val result = repository.observeUsers().first()

        assertEquals(1, result.size)
        assertEquals("Kevin", result.first().displayName)
    }

    @Test
    fun `refreshUsers clears and inserts mapped api data`() = runTest(testDispatcher) {
        whenever(dispatchersProvider.io).thenReturn(testDispatcher)
        whenever(
            userApiService.getUsers(
                order = eq("desc"),
                sort = eq("reputation"),
                filter = eq("default"),
                site = eq("stackoverflow"),
                key = eq("rl_1VQKczinVC1vhGP4f6Gx5fRmv"),
                pageSize = eq(20),
                inName = eq("kevin"),
            )
        ).thenReturn(UsersResponse(
            items = listOf(
                User(
                    accountId = 1L,
                    reputation = 2000,
                    creationDate = 999L,
                    profileImage = "profile",
                    displayName = "Kevin",
                ),
            ),
            hasMore = false,
            quotaMax = 10000,
            quotaRemaining = 9999,
        ))

        val result = repository.refreshUsers("kevin")

        assertTrue(result.isSuccess)
        verify(userDao, times(1)).clearUsers()
        verify(userDao, times(1)).insertUsers(
            argThat { users ->
                    users.size == 1 &&
                        users.first().accountId == 1L &&
                        users.first().displayName == "Kevin"
            },
        )
    }
}
