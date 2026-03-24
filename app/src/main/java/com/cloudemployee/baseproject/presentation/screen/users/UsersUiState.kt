package com.cloudemployee.baseproject.presentation.screen.users

import com.cloudemployee.baseproject.domain.model.User

data class UsersUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "kevin",
    val users: List<User> = emptyList(),
    val errorMessage: String? = null,
)
