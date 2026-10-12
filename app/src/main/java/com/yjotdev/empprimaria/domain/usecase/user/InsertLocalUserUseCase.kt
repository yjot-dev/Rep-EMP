package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserDaoRepository

class InsertLocalUserUseCase @Inject constructor(
    private val userDaoRepository: UserDaoRepository
) {
    suspend operator fun invoke(user: UserModel) {
        return userDaoRepository.insertLocalUser(user)
    }
}