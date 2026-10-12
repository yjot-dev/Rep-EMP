package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository

class GetRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(login: LoginModel): Result<UserModel>{
        return userApiRepository.getRemoteUser(login)
    }
}