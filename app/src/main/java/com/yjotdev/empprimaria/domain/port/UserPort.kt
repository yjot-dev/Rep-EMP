package com.yjotdev.empprimaria.domain.port

import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.UserEntity

interface UserPort {
    suspend fun findUser(name: String, email: String, password: String): Result<UserEntity>

    suspend fun insertUser(user: UserEntity): Result<Unit>

    suspend fun updateUser(id:Int, user: UserEntity): Result<Unit>

    suspend fun changePasswordUser(email: String, password: String): Result<Unit>

    suspend fun deleteUser(id:Int): Result<Unit>
}