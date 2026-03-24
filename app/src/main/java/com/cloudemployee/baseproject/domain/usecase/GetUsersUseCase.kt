package com.cloudemployee.baseproject.domain.usecase

import com.cloudemployee.baseproject.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.observeUsers()
}

