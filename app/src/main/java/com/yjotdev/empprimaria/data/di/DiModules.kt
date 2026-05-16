package com.yjotdev.empprimaria.data.di

import com.yjotdev.empprimaria.data.remote.api.EmailApi
import com.yjotdev.empprimaria.data.remote.api.UserApi
import com.yjotdev.empprimaria.data.remote.network.RetrofitBuilder
import com.yjotdev.empprimaria.data.repository.EmailRepositoryImpl
import com.yjotdev.empprimaria.data.repository.StringRepositoryImpl
import com.yjotdev.empprimaria.data.repository.UserRepositoryImpl
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: EmailRepositoryImpl
    ): EmailRepository

    @Binds
    @Singleton
    abstract fun bindStringRepository(
        impl: StringRepositoryImpl
    ): StringRepository

    // --- PROVIDERS (Instancias externas) ---
    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(retrofitBuilder: RetrofitBuilder): Retrofit {
            return retrofitBuilder.getRetrofitInstance()
        }

        @Provides
        @Singleton
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }

        @Provides
        @Singleton
        fun provideEmailApi(retrofit: Retrofit): EmailApi {
            return retrofit.create(EmailApi::class.java)
        }
    }
}