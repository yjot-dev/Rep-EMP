package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository

@Singleton
class ChangePasswordUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: UserEntity) {
        return userRepository.changePasswordUser(user)
    }
}