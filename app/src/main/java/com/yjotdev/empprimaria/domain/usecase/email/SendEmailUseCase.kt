package com.yjotdev.empprimaria.domain.usecase.email

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailApiRepository

class SendEmailUseCase @Inject constructor(
    private val emailApiRepository: EmailApiRepository
) {
    suspend operator fun invoke(email: EmailModel): Result<Unit> {
        return emailApiRepository.sendEmail(email)
    }
}