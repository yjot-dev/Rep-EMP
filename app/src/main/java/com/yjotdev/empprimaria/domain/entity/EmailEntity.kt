package com.yjotdev.empprimaria.domain.entity

import com.google.gson.annotations.SerializedName
/**
 Modelo para enviar correos electronicos.
 **/
data class EmailEntity(
    @SerializedName("para") val to: String,
    @SerializedName("asunto") val subject: String,
    @SerializedName("mensaje") val text: String
)