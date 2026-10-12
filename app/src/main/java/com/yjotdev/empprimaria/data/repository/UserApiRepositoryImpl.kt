package com.yjotdev.empprimaria.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.data.remote.api.UserApi
import com.yjotdev.empprimaria.data.remote.core.safeApiCallForBody
import com.yjotdev.empprimaria.data.remote.core.safeApiCallForUnit
import com.yjotdev.empprimaria.data.remote.mapper.toDomain
import com.yjotdev.empprimaria.data.remote.mapper.toDto
import com.yjotdev.empprimaria.domain.core.mapSuccess

@Singleton
class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserApiRepository {

    override suspend fun getRemoteUser(login: LoginModel): Result<UserModel> {
        return safeApiCallForBody{ userApi.findUser(login.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun insertRemoteUser(user: UserModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.insertUser(user.toDto()) }
    }

    override suspend fun updateRemoteUser(id: Int, user: UserModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.updateUser(id, user.toDto()) }
    }

    override suspend fun deleteRemoteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit{ userApi.deleteUser(id) }
    }

    override suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit> {
        return safeApiCallForUnit{ userApi.changePasswordUser(recovery.toDto()) }
    }
}