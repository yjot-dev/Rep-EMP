package com.yjotdev.empprimaria.domain.port

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity

interface EmailPort {
    suspend fun sendEmail(email: EmailEntity): Result<Unit>

    suspend fun sendCommentary(email: EmailEntity): Result<Unit>
}