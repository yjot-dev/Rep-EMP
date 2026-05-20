package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserRepository

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): Result<Unit> {
        return userRepository.insertUser(user)
    }
}