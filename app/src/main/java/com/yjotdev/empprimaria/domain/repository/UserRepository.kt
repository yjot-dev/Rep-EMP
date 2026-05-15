package com.yjotdev.empprimaria.domain.repository

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.model.UserModel

interface UserRepository {
    suspend fun findUser(login: LoginModel): Result<UserModel>

    suspend fun insertUser(user: UserModel): Result<Unit>

    suspend fun updateUser(id:Int, user: UserModel): Result<Unit>

    suspend fun changePasswordUser(recovery: RecoveryModel): Result<Unit>

    suspend fun deleteUser(id:Int): Result<Unit>
}