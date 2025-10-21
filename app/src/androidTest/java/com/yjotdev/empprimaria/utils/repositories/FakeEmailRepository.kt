package com.yjotdev.empprimaria.utils.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.core.Result

@Singleton
class FakeEmailRepository @Inject constructor(): EmailPort {
    private val emails = mutableListOf<EmailEntity>()

    override suspend fun sendCommentary(email: EmailEntity): Result<Unit> {
        emails.add(email)
        return Result.Success(Unit)
    }

    override suspend fun sendEmail(email: EmailEntity): Result<Unit> {
        emails.add(email)
        return Result.Success(Unit)
    }
}