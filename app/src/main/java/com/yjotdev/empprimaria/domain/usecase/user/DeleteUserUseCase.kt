package com.yjotdev.empprimaria.domain.usecase

import com.yjotdev.empprimaria.domain.port.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int) {
        return userRepository.deleteUser(id)
    }
}