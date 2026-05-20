package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserRepository

class FindUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(login: LoginModel): Result<UserModel>{
        return userRepository.findUser(login)
    }
}