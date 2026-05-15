package com.yjotdev.empprimaria.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("nombre") val name: String = "",
    @SerializedName("clave") val password: String = ""
)