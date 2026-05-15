package com.yjotdev.empprimaria.data.remote.mapper

import com.yjotdev.empprimaria.data.remote.dto.RecoveryDto
import com.yjotdev.empprimaria.domain.model.RecoveryModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun RecoveryDto.toDomain() = RecoveryModel(
    email = this.email,
    password = this.password
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun RecoveryModel.toDto() = RecoveryDto(
    email = this.email,
    password = this.password
)