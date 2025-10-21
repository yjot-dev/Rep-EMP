package com.yjotdev.empprimaria.infrastructure.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.empprimaria.domain.entity.EmailEntity

interface EmailApi {
    @POST("users/email")
    suspend fun sendEmail(@Body email: EmailEntity): Response<Unit>

    @POST("users/commentary")
    suspend fun sendCommentary(@Body email: EmailEntity): Response<Unit>
}