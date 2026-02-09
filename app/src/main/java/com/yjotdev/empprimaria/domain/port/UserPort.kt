package com.yjotdev.empprimaria.domain.port

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity

interface UserPort {
    suspend fun findUser(login: LoginEntity): Result<UserEntity>

    suspend fun insertUser(user: UserEntity): Result<Unit>

    suspend fun updateUser(id:Int, user: UserEntity): Result<Unit>

    suspend fun changePasswordUser(recovery: RecoveryEntity): Result<Unit>

    suspend fun deleteUser(id:Int): Result<Unit>
}