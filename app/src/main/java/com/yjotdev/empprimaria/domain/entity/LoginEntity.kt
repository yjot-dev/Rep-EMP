package com.yjotdev.empprimaria.domain.entity

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("nombre") var name: String = "",
    @SerializedName("clave") var password: String = ""
)