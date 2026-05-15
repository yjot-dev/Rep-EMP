package com.yjotdev.empprimaria.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.data.remote.core.safeApiCallForUnit
import com.yjotdev.empprimaria.data.remote.mapper.toDto
import com.yjotdev.empprimaria.data.remote.api.EmailApi

@Singleton
class EmailRepositoryImpl @Inject constructor(
    private val emailApi: EmailApi
): EmailRepository {

    override suspend fun sendEmail(email: EmailModel): Result<Unit> {
        return safeApiCallForUnit{ emailApi.sendEmail(email.toDto()) }
    }
}