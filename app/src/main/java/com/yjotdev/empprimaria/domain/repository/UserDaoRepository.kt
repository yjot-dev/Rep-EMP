package com.yjotdev.empprimaria.domain.repository

import kotlinx.coroutines.flow.Flow
import com.yjotdev.empprimaria.domain.model.UserModel

interface UserDaoRepository {
    suspend fun updateLocalUser(user: UserModel)

    suspend fun insertLocalUser(user: UserModel)

    suspend fun deleteLocalUser(user: UserModel)

    fun getLocalUser(): Flow<UserModel>
}