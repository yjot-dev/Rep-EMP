package com.yjotdev.empprimaria.utils.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.repository.UserRepository
import com.yjotdev.empprimaria.data.di.DiModules
import com.yjotdev.empprimaria.utils.repositories.FakeEmailRepositoryImpl
import com.yjotdev.empprimaria.utils.repositories.FakeStringRepositoryImpl
import com.yjotdev.empprimaria.utils.repositories.FakeUserRepositoryImpl

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DiModules::class] // Nombre del módulo real
)
@Suppress("unused")
abstract class DiModulesTest {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindFakeUserRepository(
        impl: FakeUserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindFakeEmailRepository(
        impl: FakeEmailRepositoryImpl
    ): EmailRepository

    @Binds
    @Singleton
    abstract fun bindFakeStringRepository(
        impl: FakeStringRepositoryImpl
    ): StringRepository

    // --- PROVIDERS (Instancias externas) ---
    // No son necesarios aqui
}