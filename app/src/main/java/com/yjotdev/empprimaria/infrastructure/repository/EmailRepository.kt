package com.yjotdev.empprimaria.infrastructure.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.infrastructure.network.client.Api
import com.yjotdev.empprimaria.infrastructure.network.core.safeApiCallForUnit

@Singleton
class EmailRepository @Inject constructor(
    private val api: Api
): EmailPort {
    override suspend fun sendEmail(email: EmailEntity): Result<Unit> {
        return safeApiCallForUnit{ api.getEmailRetrofit().sendEmail(email) }
    }
}