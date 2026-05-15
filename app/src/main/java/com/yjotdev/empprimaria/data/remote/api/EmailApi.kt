package com.yjotdev.empprimaria.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.empprimaria.data.remote.dto.EmailDto

interface EmailApi {
    @POST("oauth/email")
    suspend fun sendEmail(@Body email: EmailDto): Response<Unit>
}