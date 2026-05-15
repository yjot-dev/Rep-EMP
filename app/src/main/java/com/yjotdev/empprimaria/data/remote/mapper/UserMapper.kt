package com.yjotdev.empprimaria.data.remote.mapper

import com.yjotdev.empprimaria.data.remote.dto.UserDto
import com.yjotdev.empprimaria.domain.model.UserModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun UserDto.toDomain() = UserModel(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    photo = this.photo,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun UserModel.toDto() = UserDto(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    photo = this.photo,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)