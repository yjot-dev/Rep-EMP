package com.yjotdev.empprimaria.domain.usecase

import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository

class UpdateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int, user: UserEntity) {
        return userRepository.updateUser(id, user)
    }
}