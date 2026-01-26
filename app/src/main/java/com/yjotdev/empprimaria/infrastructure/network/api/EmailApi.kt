package com.yjotdev.empprimaria.infrastructure.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.empprimaria.domain.entity.EmailEntity

interface EmailApi {
    @POST("oauth/email")
    suspend fun sendEmail(@Body email: EmailEntity): Response<Unit>

    @POST("oauth/feedback")
    suspend fun sendCommentary(@Body email: EmailEntity): Response<Unit>
}