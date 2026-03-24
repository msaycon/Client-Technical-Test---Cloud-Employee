package com.cloudemployee.baseproject.domain.usecase

import com.cloudemployee.baseproject.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(accountId: Long) = repository.observeUser(accountId)
}
