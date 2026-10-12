package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository

class InsertRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(user: UserModel): Result<Unit> {
        return userApiRepository.insertRemoteUser(user)
    }
}