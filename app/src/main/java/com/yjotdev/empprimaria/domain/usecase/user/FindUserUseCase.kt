package com.yjotdev.empprimaria.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserRepository

@Singleton
class FindUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: UserEntity): UserEntity{
        return userRepository.findUser(user)
    }
}