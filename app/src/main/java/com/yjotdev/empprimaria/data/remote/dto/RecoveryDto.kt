package com.yjotdev.empprimaria.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecoveryDto(
    @SerializedName("correo") val email: String = "",
    @SerializedName("clave") val password: String = ""
)