package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserPort

@Singleton
class FindUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    suspend operator fun invoke(login: LoginEntity): Result<UserEntity>{
        return userPort.findUser(login)
    }
}