package com.yjotdev.empprimaria.domain.usecase.email

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailPort

@Singleton
class SendEmailUseCase @Inject constructor(
    private val emailPort: EmailPort
) {
    suspend operator fun invoke(email: EmailEntity): Result<Unit> {
        return emailPort.sendEmail(email)
    }
}