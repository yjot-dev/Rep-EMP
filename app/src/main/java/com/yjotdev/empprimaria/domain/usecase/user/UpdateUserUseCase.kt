package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserPort

@Singleton
class UpdateUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    suspend operator fun invoke(id: Int, user: UserEntity): Result<Unit> {
        return userPort.updateUser(id, user)
    }
}