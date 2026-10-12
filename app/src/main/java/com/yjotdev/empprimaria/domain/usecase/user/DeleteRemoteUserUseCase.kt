package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.repository.UserApiRepository

class DeleteRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userApiRepository.deleteRemoteUser(id)
    }
}