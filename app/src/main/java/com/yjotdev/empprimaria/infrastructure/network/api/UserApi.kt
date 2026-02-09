package com.yjotdev.empprimaria.infrastructure.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Headers
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun findUser(@Body login: LoginEntity): Response<UserEntity>

    @POST("users")
    suspend fun insertUser(@Body user: UserEntity): Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id:Int, @Body user: UserEntity): Response<Unit>

    @PATCH("users")
    suspend fun changePasswordUser(@Body recovery: RecoveryEntity): Response<Unit>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id:Int): Response<Unit>
}