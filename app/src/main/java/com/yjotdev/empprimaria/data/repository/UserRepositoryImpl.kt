package com.yjotdev.empprimaria.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserRepository
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.data.remote.api.UserApi
import com.yjotdev.empprimaria.data.remote.core.safeApiCallForBody
import com.yjotdev.empprimaria.data.remote.core.safeApiCallForUnit
import com.yjotdev.empprimaria.data.remote.mapper.toDomain
import com.yjotdev.empprimaria.data.remote.mapper.toDto
import com.yjotdev.empprimaria.domain.core.mapSuccess

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    override suspend fun findUser(login: LoginModel): Result<UserModel> {
        return safeApiCallForBody{ userApi.findUser(login.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun insertUser(user: UserModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.insertUser(user.toDto()) }
    }

    override suspend fun updateUser(id: Int, user: UserModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.updateUser(id, user.toDto()) }
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit{ userApi.deleteUser(id) }
    }

    override suspend fun changePasswordUser(recovery: RecoveryModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.changePasswordUser(recovery.toDto()) }
    }
}