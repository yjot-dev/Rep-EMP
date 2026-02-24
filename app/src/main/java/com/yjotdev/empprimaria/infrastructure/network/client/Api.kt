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

@Singleton
class Api @Inject constructor(
    @ApplicationContext context: Context
) {
    private val uri = if (BuildConfig.DEBUG) { "https://192.168.1.20:3000/api/" }
                      else { "https://emp-yjotdev.up.railway.app/api/" }
    private val httpsClient = if (BuildConfig.DEBUG) { Client.getUnsafeClient(context) }
                              else { Client.getSafeClient() }

    /** API Tabla Usuario **/
    fun getUserRetrofit(): UserApi = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
        .create(UserApi::class.java)

    /** API Gmail **/
    fun getEmailRetrofit(): EmailApi = Retrofit.Builder()
        .baseUrl(uri)
        .client(httpsClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
        .create(EmailApi::class.java)
}