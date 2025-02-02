package com.yjotdev.empprimaria.api_client

import com.yjotdev.empprimaria.api_interface.UserInterface
import com.yjotdev.empprimaria.ui.model.EmailModel
import com.yjotdev.empprimaria.ui.model.UserModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
* 10.0.2.2 -> IP para probar API desde emulador
* 192.168.1.9 -> IP para probar API desde dispositivo fisico
*/
class UserClient {
    private fun getRetrofit(): UserInterface = Retrofit.Builder()
        .baseUrl("https://192.168.1.9:443/api/")
        .client(getClient())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserInterface::class.java)

    private fun getClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    suspend fun findUser(user: UserModel): UserModel?{
        val response = getRetrofit().findUser(user)
        return if(response.isSuccessful) response.body() else null
    }

    suspend fun insertUser(user: UserModel): Boolean{
        val response = getRetrofit().insertUser(user)
        return response.isSuccessful
    }

    suspend fun updateUser(id: Int, user: UserModel): Boolean{
        val response = getRetrofit().updateUser(id, user)
        return response.isSuccessful
    }

    suspend fun changePasswordUser(user: UserModel): Boolean{
        val response = getRetrofit().changePasswordUser(user)
        return response.isSuccessful
    }

    suspend fun deleteUser(id: Int): Boolean{
        val response = getRetrofit().deleteUser(id)
        return response.isSuccessful
    }

    suspend fun sendEmail(email: EmailModel): Boolean{
        val response = getRetrofit().sendEmail(email)
        return response.isSuccessful
    }

    suspend fun sendCommentary(email: EmailModel): Boolean{
        val response = getRetrofit().sendCommentary(email)
        return response.isSuccessful
    }
}