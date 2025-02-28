package com.yjotdev.empprimaria.api_interface

import com.yjotdev.empprimaria.ui.model.EmailModel
import com.yjotdev.empprimaria.ui.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserInterface {
    @POST("find_user/")
    suspend fun findUser(@Body user:UserModel): Response<UserModel>

    @POST("insert_user/")
    suspend fun insertUser(@Body user:UserModel): Response<Unit>

    @PUT("update_user/{id}")
    suspend fun updateUser(@Path("id") id:Int, @Body user:UserModel): Response<Unit>

    @PATCH("change_password/")
    suspend fun changePasswordUser(@Body user:UserModel): Response<Unit>

    @DELETE("delete_user/{id}")
    suspend fun deleteUser(@Path("id") id:Int): Response<Unit>

    @POST("send_email/")
    suspend fun sendEmail(@Body email:EmailModel): Response<Unit>

    @POST("send_commentary/")
    suspend fun sendCommentary(@Body email:EmailModel): Response<Unit>
}