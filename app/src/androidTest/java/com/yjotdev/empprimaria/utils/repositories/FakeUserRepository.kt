package com.yjotdev.empprimaria.utils.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.domain.core.Result

@Singleton
class FakeUserRepository @Inject constructor() : UserPort {
    private val users = mutableListOf(UserEntity(
        id = 1,
        name = "yasser",
        email = "2010guabo@gmail.com",
        password = "Yjot1997",
        photo = ""
    ))

    override suspend fun findUser(name: String, email: String, password: String): Result<UserEntity> {
        users[0] = users[0].copy(
            name = name,
            email = email,
            password = password
        )
        return Result.Success(users[0])
    }

    override suspend fun insertUser(user: UserEntity): Result<Unit> {
        users.add(user)
        return Result.Success(Unit)
    }

    override suspend fun updateUser(id: Int, user: UserEntity): Result<Unit> {
        users[id] = user
        return Result.Success(Unit)
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        users.removeAt(id)
        return Result.Success(Unit)
    }

    override suspend fun changePasswordUser(email: String, password: String): Result<Unit> {
        users[0] = users[0].copy(
            email = email,
            password = password
        )
        return Result.Success(Unit)
    }
}