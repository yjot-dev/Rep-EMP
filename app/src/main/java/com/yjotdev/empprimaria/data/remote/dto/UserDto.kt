package com.yjotdev.empprimaria.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val name: String = "",
    @SerializedName("correo") val email: String = "",
    @SerializedName("clave") val password: String = "",
    @SerializedName("foto") val photo: String = "",
    @SerializedName("esInvitado") val isInvited: Boolean = false,
    @SerializedName("estaEnListaBlanca") val isInWhiteList: Boolean = false
)