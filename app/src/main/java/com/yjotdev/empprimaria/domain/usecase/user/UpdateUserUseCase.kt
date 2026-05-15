package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserRepository

@Singleton
class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int, user: UserModel): Result<Unit> {
        return userRepository.updateUser(id, user)
    }
}