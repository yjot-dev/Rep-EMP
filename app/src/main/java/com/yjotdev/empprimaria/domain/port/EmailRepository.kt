package com.yjotdev.empprimaria.domain.port

import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.empprimaria.domain.entity.EmailEntity

interface EmailRepository {
    @POST("send_email/")
    suspend fun sendEmail(@Body email: EmailEntity)

    @POST("send_commentary/")
    suspend fun sendCommentary(@Body email: EmailEntity)
}