package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.repository.UserRepository

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userRepository.deleteUser(id)
    }
}