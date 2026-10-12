package com.yjotdev.empprimaria.utils.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.repository.EmailApiRepository
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.repository.UserApiRepository
import com.yjotdev.empprimaria.domain.repository.UserDaoRepository
import com.yjotdev.empprimaria.data.di.DiModules
import com.yjotdev.empprimaria.utils.repository.FakeEmailApiRepositoryImpl
import com.yjotdev.empprimaria.utils.repository.FakeStringRepositoryImpl
import com.yjotdev.empprimaria.utils.repository.FakeUserApiRepositoryImpl
import com.yjotdev.empprimaria.utils.repository.FakeUserDaoRepositoryImpl

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
    abstract fun bindFakeUserApiRepository(
        impl: FakeUserApiRepositoryImpl
    ): UserApiRepository

    @Binds
    @Singleton
    abstract fun bindFakeUserDaoRepository(
        impl: FakeUserDaoRepositoryImpl
    ): UserDaoRepository

    @Binds
    @Singleton
    abstract fun bindFakeEmailApiRepository(
        impl: FakeEmailApiRepositoryImpl
    ): EmailApiRepository

    @Binds
    @Singleton
    abstract fun bindFakeStringRepository(
        impl: FakeStringRepositoryImpl
    ): StringRepository

    // --- PROVIDERS (Instancias externas) ---
    // No son necesarios aqui
}