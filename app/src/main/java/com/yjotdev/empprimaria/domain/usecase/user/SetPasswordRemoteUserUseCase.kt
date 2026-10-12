package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository

class SetPasswordRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(recovery: RecoveryModel): Result<Unit> {
        return userApiRepository.setPasswordRemoteUser(recovery)
    }
}