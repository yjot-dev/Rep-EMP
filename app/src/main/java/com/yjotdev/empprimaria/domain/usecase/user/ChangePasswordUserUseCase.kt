package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.port.UserPort

@Singleton
class ChangePasswordUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return userPort.changePasswordUser(email, password)
    }
}