package com.yjotdev.empprimaria.utils.di

import android.content.Context
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Provides
import dagger.Binds
import dagger.hilt.android.qualifiers.ApplicationContext
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

    companion object {
        @Provides
        @Singleton
        fun provideTestNavHostController(@ApplicationContext context: Context) =
            TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
    }
}