package com.yjotdev.empprimaria.ui.model

import com.google.gson.annotations.SerializedName

/**
Modelo para enviar y recibir los datos del usuario
en la BD.
 **/
data class UserModel(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("correo") val correo: String = "",
    @SerializedName("clave") val clave: String = "",
    @SerializedName("foto") val foto: String = ""
)