package com.yjotdev.empprimaria.infrastructure.adapter

import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import com.yjotdev.empprimaria.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.yjotdev.empprimaria.domain.port.UserRepository
import com.yjotdev.empprimaria.domain.port.EmailRepository

/*
* 10.0.2.2 -> IP para probar API desde emulador
* 192.168.1.20 -> IP para probar API desde dispositivo fisico
*/
@Singleton
class HttpsClient @Inject constructor(
    @ApplicationContext context: Context
) {
    private val uri = "https://192.168.1.20:443/api/"
    private val httpsClient = if (BuildConfig.DEBUG) {
        Client.getUnsafeClient(context) }
    else {
        Client.getSafeClient()
    }

    /** API Tabla Usuario **/
    fun getUserRetrofit(): UserRepository = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserRepository::class.java)

    /** API Tabla Email **/
    fun getEmailRetrofit(): EmailRepository = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmailRepository::class.java)
}