package com.yjotdev.empprimaria.domain.model

data class UserModel(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val photo: String = "",
    val isInvited: Boolean = false,
    val isInWhiteList: Boolean = false
)