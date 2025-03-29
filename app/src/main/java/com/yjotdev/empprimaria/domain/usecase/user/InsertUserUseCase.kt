package com.yjotdev.empprimaria.domain.usecase

import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository

class InsertUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: UserEntity) {
        return userRepository.insertUser(user)
    }
}