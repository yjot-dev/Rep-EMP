package com.yjotdev.empprimaria.infrastructure.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.infrastructure.network.client.Api
import com.yjotdev.empprimaria.infrastructure.network.core.safeApiCallForBody
import com.yjotdev.empprimaria.infrastructure.network.core.safeApiCallForUnit

@Singleton
class UserRepository @Inject constructor(
    private val api: Api
): UserPort {
    override suspend fun findUser(name: String, email: String, password: String): Result<UserEntity> {
        val user = UserEntity(0, name, email, password, "")
        return safeApiCallForBody{ api.getUserRetrofit().findUser(user) }
    }

    override suspend fun insertUser(user: UserEntity): Result<Unit> {
        return safeApiCallForUnit{ api.getUserRetrofit().insertUser(user) }
    }

    override suspend fun updateUser(id: Int, user: UserEntity): Result<Unit> {
        return safeApiCallForUnit{ api.getUserRetrofit().updateUser(id, user) }
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit{ api.getUserRetrofit().deleteUser(id) }
    }

    override suspend fun changePasswordUser(email: String, password: String): Result<Unit> {
        val user = UserEntity(0, "", email, password, "")
        return safeApiCallForUnit{ api.getUserRetrofit().changePasswordUser(user) }
    }
}