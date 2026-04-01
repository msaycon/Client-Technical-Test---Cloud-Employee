package com.cloudemployee.baseproject.presentation.screen.users

import com.cloudemployee.baseproject.domain.model.User
import com.cloudemployee.baseproject.domain.usecase.GetUsersUseCase
import com.cloudemployee.baseproject.domain.usecase.RefreshUsersUseCase
import com.cloudemployee.baseproject.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersUseCase = mock<GetUsersUseCase>()
    private val refreshUsersUseCase = mock<RefreshUsersUseCase>()

    @Test
    fun `init loads users into ui state`() = runTest(mainDispatcherRule.dispatcher) {
        whenever(getUsersUseCase.invoke()).thenReturn(
            flowOf(
            listOf(
                User(
                    accountId = 1L,
                    reputation = 100,
                    creationDate = 1000L,
                    profileImage = "image",
                    displayName = "Kevin",
                ),
            ),
            ),
        )
        whenever(refreshUsersUseCase.invoke(any())).thenReturn(Result.success(Unit))

        val viewModel = UsersViewModel(
            getUsersUseCase = getUsersUseCase,
            refreshUsersUseCase = refreshUsersUseCase,
        )

        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Kevin", viewModel.uiState.value.users.first().displayName)
        verify(refreshUsersUseCase, times(1)).invoke("kevin")
    }

    @Test
    fun `refreshUsers uses updated search query`() = runTest(mainDispatcherRule.dispatcher) {
        whenever(getUsersUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(refreshUsersUseCase.invoke(any())).thenReturn(Result.success(Unit))

        val viewModel = UsersViewModel(
            getUsersUseCase = getUsersUseCase,
            refreshUsersUseCase = refreshUsersUseCase,
        )

        advanceUntilIdle()
        clearInvocations(refreshUsersUseCase)

        viewModel.onSearchQueryChange("john")
        viewModel.refreshUsers()
        advanceUntilIdle()

        verify(refreshUsersUseCase, times(1)).invoke("john")
        assertNull(viewModel.uiState.value.errorMessage)
    }
}
