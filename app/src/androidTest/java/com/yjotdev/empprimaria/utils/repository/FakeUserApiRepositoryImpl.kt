package com.yjotdev.empprimaria.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel

@Singleton
class FakeUserApiRepositoryImpl @Inject constructor() : UserApiRepository {

    override suspend fun getRemoteUser(login: LoginModel): Result<UserModel> {
        return if (login != LoginModel()){
            Result.Success(
                UserModel(
                    id = 1,
                    name = "Yasser",
                    email = "yasser@gmail.com"
                )
            )
        }else {
            Result.Error(Exception("Error al encontrar el usuario"))
        }
    }

    override suspend fun insertRemoteUser(user: UserModel): Result<Unit> {
        return if (user != UserModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al insertar el usuario"))
        }
    }

    override suspend fun updateRemoteUser(id: Int, user: UserModel): Result<Unit> {
        return if (user != UserModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al actualizar el usuario"))
        }
    }

    override suspend fun deleteRemoteUser(id: Int): Result<Unit> {
        return if (id != 0){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al eliminar el usuario"))
        }
    }

    override suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit> {
        return if (recovery != RecoveryModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al cambiar la contraseña"))
        }
    }
}