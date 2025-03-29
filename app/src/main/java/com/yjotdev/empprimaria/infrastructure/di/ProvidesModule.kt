package com.yjotdev.empprimaria.infrastructure.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.EmailRepository
import com.yjotdev.empprimaria.domain.port.UserRepository
import com.yjotdev.empprimaria.infrastructure.adapter.Api
import com.yjotdev.empprimaria.infrastructure.repositories.EmailRepositoryImpl
import com.yjotdev.empprimaria.infrastructure.repositories.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideUserRepositoryImpl(api: Api): UserRepository =
        UserRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideEmailRepositoryImpl(api: Api): EmailRepository =
        EmailRepositoryImpl(api)
}