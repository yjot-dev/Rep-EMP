package com.yjotdev.empprimaria.utils.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailRepository

@Singleton
class FakeEmailRepositoryImpl @Inject constructor(): EmailRepository {
    private val emails = mutableListOf<EmailEntity>()

    override suspend fun sendCommentary(email: EmailEntity) {
        emails.add(email)
    }

    override suspend fun sendEmail(email: EmailEntity) {
        emails.add(email)
    }
}