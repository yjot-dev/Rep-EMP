package com.yjotdev.empprimaria.domain.repository

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.EmailModel

interface EmailRepository {
    suspend fun sendEmail(email: EmailModel): Result<Unit>
}