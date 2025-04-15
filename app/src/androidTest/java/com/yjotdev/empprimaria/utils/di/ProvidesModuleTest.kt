package com.yjotdev.empprimaria.utils.di

import android.content.Context
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.EmailRepository
import com.yjotdev.empprimaria.domain.port.UserRepository
import com.yjotdev.empprimaria.infrastructure.di.ProvidesModule
import com.yjotdev.empprimaria.utils.repositories.FakeEmailRepositoryImpl
import com.yjotdev.empprimaria.utils.repositories.FakeUserRepositoryImpl

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProvidesModule::class] // Nombre del módulo real
)
object ProvidesModuleTest {
    @Singleton
    @Provides
    fun provideFakeUserRepositoryImpl(): UserRepository =
        FakeUserRepositoryImpl()

    @Singleton
    @Provides
    fun provideFakeEmailRepositoryImpl(): EmailRepository =
        FakeEmailRepositoryImpl()

    @Singleton
    @Provides
    fun provideTestNavHostController(@ApplicationContext context: Context) =
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
}