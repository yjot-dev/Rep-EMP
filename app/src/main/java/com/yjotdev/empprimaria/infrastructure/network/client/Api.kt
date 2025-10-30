package com.yjotdev.empprimaria.infrastructure.network.client

import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.yjotdev.empprimaria.BuildConfig
import com.yjotdev.empprimaria.infrastructure.network.api.UserApi
import com.yjotdev.empprimaria.infrastructure.network.api.EmailApi
import com.yjotdev.empprimaria.infrastructure.network.core.NullOnEmptyConverterFactory

/*
* 10.0.2.2 -> IP para probar API desde emulador
* 192.168.1.20 -> IP para probar API desde dispositivo fisico
*/
@Singleton
class Api @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val uri = "https://192.168.1.20:443/api/"
    private val httpsClient = if (BuildConfig.DEBUG) {
        Client.getUnsafeClient(context) }
    else {
        Client.getSafeClient()
    }

    /** API Tabla Usuario **/
    fun getUserRetrofit(): UserApi = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApi::class.java)

    /** API Tabla Email **/
    fun getEmailRetrofit(): EmailApi = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmailApi::class.java)
}