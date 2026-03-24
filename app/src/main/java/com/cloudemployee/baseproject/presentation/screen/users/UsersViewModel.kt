package com.cloudemployee.baseproject.presentation.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudemployee.baseproject.domain.usecase.GetUsersUseCase
import com.cloudemployee.baseproject.domain.usecase.RefreshUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUsersUseCase().collect { users ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        users = users,
                    )
                }
            }
        }

        refreshUsers()
    }

    fun onSearchQueryChange(value: String) {
        _uiState.update { state -> state.copy(searchQuery = value) }
    }

    fun refreshUsers() {
        viewModelScope.launch {
            val query = uiState.value.searchQuery.trim().ifEmpty { "kevin" }
            _uiState.update { state ->
                state.copy(
                    isRefreshing = true,
                    errorMessage = null,
                )
            }

            refreshUsersUseCase(query)
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to refresh users.",
                        )
                    }
                }

            _uiState.update { state ->
                state.copy(isRefreshing = false)
            }
        }
    }
}
