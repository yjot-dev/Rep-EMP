package com.yjotdev.empprimaria.utils.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.infrastructure.di.DiModules
import com.yjotdev.empprimaria.utils.repositories.FakeEmailRepository
import com.yjotdev.empprimaria.utils.repositories.FakeUserRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DiModules::class] // Nombre del módulo real
)
@Suppress("unused")
abstract class DiModulesTest {
    @Binds
    @Singleton
    abstract fun bindFakeUserRepository(
        impl: FakeUserRepository
    ): UserPort

    @Binds
    @Singleton
    abstract fun bindFakeEmailRepository(
        impl: FakeEmailRepository
    ): EmailPort
}