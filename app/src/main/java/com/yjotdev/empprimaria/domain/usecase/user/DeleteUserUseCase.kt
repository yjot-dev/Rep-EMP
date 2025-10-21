package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.port.UserPort

@Singleton
class DeleteUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userPort.deleteUser(id)
    }
}