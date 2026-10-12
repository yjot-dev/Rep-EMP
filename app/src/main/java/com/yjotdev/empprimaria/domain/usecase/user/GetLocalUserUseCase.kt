package com.yjotdev.empprimaria.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserDaoRepository

class GetLocalUserUseCase @Inject constructor(
    private val userDaoRepository: UserDaoRepository
) {
    operator fun invoke(): Flow<UserModel> {
        return userDaoRepository.getLocalUser()
    }
}