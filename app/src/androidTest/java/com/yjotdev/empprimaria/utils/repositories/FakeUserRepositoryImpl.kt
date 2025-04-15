package com.yjotdev.empprimaria.utils.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository

@Singleton
class FakeUserRepositoryImpl @Inject constructor() : UserRepository {
    private val users = mutableListOf(UserEntity(
        id = 1,
        nombre = "yasser",
        correo = "2010guabo@gmail.com",
        clave = "Yjot1997",
        foto = ""
    ))

    override suspend fun findUser(user: UserEntity): UserEntity {
        return users[0]
    }

    override suspend fun insertUser(user: UserEntity) {
        users.add(user)
    }

    override suspend fun updateUser(id: Int, user: UserEntity) {
        users[id] = user
    }

    override suspend fun deleteUser(id: Int) {
        users.removeAt(id)
    }

    override suspend fun changePasswordUser(user: UserEntity) {
        users[0] = user
    }
}