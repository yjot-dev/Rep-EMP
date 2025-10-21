package com.yjotdev.empprimaria.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.infrastructure.adapter.Api
import com.yjotdev.empprimaria.infrastructure.repositories.EmailRepository
import com.yjotdev.empprimaria.infrastructure.repositories.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Singleton
    @Provides
    fun provideUserRepository(api: Api): UserPort =
        UserRepository(api)

    @Singleton
    @Provides
    fun provideEmailRepository(api: Api): EmailPort =
        EmailRepository(api)
}