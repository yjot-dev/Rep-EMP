package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.repository.UserRepository

@Singleton
class ChangePasswordUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(recovery: RecoveryModel): Result<Unit> {
        return userRepository.changePasswordUser(recovery)
    }
}