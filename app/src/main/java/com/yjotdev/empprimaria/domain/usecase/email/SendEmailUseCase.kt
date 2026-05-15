package com.yjotdev.empprimaria.domain.usecase.email

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailRepository

@Singleton
class SendEmailUseCase @Inject constructor(
    private val emailRepository: EmailRepository
) {
    suspend operator fun invoke(email: EmailModel): Result<Unit> {
        return emailRepository.sendEmail(email)
    }
}