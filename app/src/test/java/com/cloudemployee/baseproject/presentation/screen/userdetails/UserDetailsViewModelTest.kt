package com.cloudemployee.baseproject.presentation.screen.userdetails

import androidx.lifecycle.SavedStateHandle
import com.cloudemployee.baseproject.domain.model.User
import com.cloudemployee.baseproject.domain.usecase.GetUserDetailsUseCase
import com.cloudemployee.baseproject.presentation.navigation.Routes
import com.cloudemployee.baseproject.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUserDetailsUseCase = mock<GetUserDetailsUseCase>()

    @Test
    fun `init loads selected user details into ui state`() = runTest(mainDispatcherRule.dispatcher) {
        whenever(getUserDetailsUseCase.invoke(1L)).thenReturn(
            flowOf(
            User(
                accountId = 1L,
                reputation = 500,
                creationDate = 123456L,
                profileImage = "image",
                displayName = "Kevin",
            ),
            ),
        )

        val viewModel = UserDetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(Routes.UserDetails.accountIdArg to 1L),
            ),
            getUserDetailsUseCase = getUserDetailsUseCase,
        )

        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Kevin", viewModel.uiState.value.user?.displayName)
    }
}
