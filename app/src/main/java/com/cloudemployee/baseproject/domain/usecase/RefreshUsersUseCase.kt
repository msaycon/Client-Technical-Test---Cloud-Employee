package com.cloudemployee.baseproject.domain.usecase

import com.cloudemployee.baseproject.domain.repository.UserRepository
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(inName: String): Result<Unit> = repository.refreshUsers(inName)
}
