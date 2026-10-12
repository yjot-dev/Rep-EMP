package com.yjotdev.empprimaria.domain.repository

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.model.UserModel

interface UserApiRepository {
    suspend fun getRemoteUser(login: LoginModel): Result<UserModel>

    suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit>

    suspend fun insertRemoteUser(user: UserModel): Result<Unit>

    suspend fun updateRemoteUser(id:Int, user: UserModel): Result<Unit>

    suspend fun deleteRemoteUser(id:Int): Result<Unit>
}