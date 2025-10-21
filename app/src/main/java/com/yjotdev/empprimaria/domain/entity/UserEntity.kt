package com.yjotdev.empprimaria.domain.entity

import com.google.gson.annotations.SerializedName

/**
Modelo para enviar y recibir los datos del usuario
en la BD.
 **/
data class UserEntity(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val name: String = "",
    @SerializedName("correo") val email: String = "",
    @SerializedName("clave") val password: String = "",
    @SerializedName("foto") val photo: String = ""
)