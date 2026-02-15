package com.yjotdev.empprimaria.infrastructure.di

import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.port.StringPort
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.infrastructure.repository.EmailRepository
import com.yjotdev.empprimaria.infrastructure.repository.StringRepository
import com.yjotdev.empprimaria.infrastructure.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepository
    ): UserPort

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: EmailRepository
    ): EmailPort

    @Binds
    @Singleton
    abstract fun bindStringRepository(
        impl: StringRepository
    ): StringPort
}