package com.cloudemployee.baseproject.presentation.screen.userdetails

import com.cloudemployee.baseproject.domain.model.User

data class UserDetailsUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
)

