package com.cloudemployee.baseproject.presentation.screen.userdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudemployee.baseproject.domain.usecase.GetUserDetailsUseCase
import com.cloudemployee.baseproject.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getUserDetailsUseCase: GetUserDetailsUseCase,
) : ViewModel() {

    private val accountId: Long = checkNotNull(savedStateHandle[Routes.UserDetails.accountIdArg])

    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserDetailsUseCase(accountId).collectLatest { user ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        user = user,
                    )
                }
            }
        }
    }
}

