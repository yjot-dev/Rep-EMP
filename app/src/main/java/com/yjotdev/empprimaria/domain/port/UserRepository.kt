package com.yjotdev.empprimaria.domain.port

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.yjotdev.empprimaria.domain.entity.UserEntity

interface UserRepository {
    @POST("find_user/")
    suspend fun findUser(@Body user: UserEntity): UserEntity

    @POST("insert_user/")
    suspend fun insertUser(@Body user: UserEntity)

    @PUT("update_user/{id}")
    suspend fun updateUser(@Path("id") id:Int, @Body user: UserEntity)

    @PATCH("change_password/")
    suspend fun changePasswordUser(@Body user: UserEntity)

    @DELETE("delete_user/{id}")
    suspend fun deleteUser(@Path("id") id:Int)
}