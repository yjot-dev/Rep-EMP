package com.yjotdev.empprimaria.data.di

import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import com.yjotdev.empprimaria.data.remote.network.RetrofitBuilder
import com.yjotdev.empprimaria.data.remote.api.UserApi
import com.yjotdev.empprimaria.data.remote.api.EmailApi
import com.yjotdev.empprimaria.data.repository.EmailRepositoryImpl
import com.yjotdev.empprimaria.data.repository.StringRepositoryImpl
import com.yjotdev.empprimaria.data.repository.UserRepositoryImpl
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.repository.UserRepository

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
        fun provideUserService(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }

        @Provides
        @Singleton
        fun provideEmailService(retrofit: Retrofit): EmailApi {
            return retrofit.create(EmailApi::class.java)
        }
    }
}