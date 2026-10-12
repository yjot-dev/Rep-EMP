package com.yjotdev.empprimaria.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserDaoRepository

@Singleton
class FakeUserDaoRepositoryImpl @Inject constructor()
    : UserDaoRepository {
    private val userFlow = MutableStateFlow(UserModel())

    override suspend fun updateLocalUser(user: UserModel) {
        userFlow.value = user
    }

    override suspend fun insertLocalUser(user: UserModel) {
        userFlow.value = user
    }

    override suspend fun deleteLocalUser(user: UserModel) {
        userFlow.value = UserModel()
    }

    override fun getLocalUser(): Flow<UserModel> {
        return userFlow
    }
}