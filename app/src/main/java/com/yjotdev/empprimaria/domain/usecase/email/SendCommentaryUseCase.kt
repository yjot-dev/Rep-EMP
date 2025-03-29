package com.yjotdev.empprimaria.domain.usecase.email

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailRepository

@Singleton
class SendCommentaryUseCase @Inject constructor(private val emailRepository: EmailRepository) {
    suspend operator fun invoke(email: EmailEntity) {
        return emailRepository.sendCommentary(email)
    }
}