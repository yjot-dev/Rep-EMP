package com.yjotdev.empprimaria.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Headers
import com.yjotdev.empprimaria.data.remote.dto.LoginDto
import com.yjotdev.empprimaria.data.remote.dto.RecoveryDto
import com.yjotdev.empprimaria.data.remote.dto.UserDto

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun findUser(@Body login: LoginDto): Response<UserDto>

    @POST("users")
    suspend fun insertUser(@Body user: UserDto): Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id:Int, @Body user: UserDto): Response<Unit>

    @PATCH("users")
    suspend fun changePasswordUser(@Body recovery: RecoveryDto): Response<Unit>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id:Int): Response<Unit>
}