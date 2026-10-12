package com.yjotdev.empprimaria.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton
import com.yjotdev.empprimaria.data.remote.api.EmailApi
import com.yjotdev.empprimaria.data.remote.api.UserApi
import com.yjotdev.empprimaria.data.remote.network.RetrofitBuilder
import com.yjotdev.empprimaria.data.local.database.EmpDatabase
import com.yjotdev.empprimaria.data.local.dao.UserDao
import com.yjotdev.empprimaria.data.repository.EmailApiRepositoryImpl
import com.yjotdev.empprimaria.data.repository.StringRepositoryImpl
import com.yjotdev.empprimaria.data.repository.UserApiRepositoryImpl
import com.yjotdev.empprimaria.data.repository.UserDaoRepositoryImpl
import com.yjotdev.empprimaria.domain.repository.EmailApiRepository
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.repository.UserApiRepository
import com.yjotdev.empprimaria.domain.repository.UserDaoRepository

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindUserApiRepository(
        impl: UserApiRepositoryImpl
    ): UserApiRepository

    @Binds
    @Singleton
    abstract fun bindUserDaoRepository(
        impl: UserDaoRepositoryImpl
    ): UserDaoRepository

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: EmailApiRepositoryImpl
    ): EmailApiRepository

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

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): EmpDatabase =
            Room.databaseBuilder(
                context,
                EmpDatabase::class.java,
                EmpDatabase.NAME
            ).fallbackToDestructiveMigration(true).build()

        @Provides
        @Singleton
        fun provideUserDao(database: EmpDatabase): UserDao =
            database.userDao()
    }
}