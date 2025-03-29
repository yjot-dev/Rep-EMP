package com.yjotdev.empprimaria.infrastructure.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository
import com.yjotdev.empprimaria.infrastructure.adapter.Api

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: Api
): UserRepository {
    /** Encuentra el usuario indicado **/
    override suspend fun findUser(user: UserEntity): UserEntity {
        return api.getUserRetrofit().findUser(user)
    }

    /** Inserta un usuario **/
    override suspend fun insertUser(user: UserEntity) {
        return api.getUserRetrofit().insertUser(user)
    }

    /** Actualiza un usuario **/
    override suspend fun updateUser(id: Int, user: UserEntity) {
        return api.getUserRetrofit().updateUser(id, user)
    }

    /** Borra un usuario **/
    override suspend fun deleteUser(id: Int) {
        return api.getUserRetrofit().deleteUser(id)
    }

    /** Cambia la clave del usuario indicado **/
    override suspend fun changePasswordUser(user: UserEntity) {
        return api.getUserRetrofit().changePasswordUser(user)
    }
}