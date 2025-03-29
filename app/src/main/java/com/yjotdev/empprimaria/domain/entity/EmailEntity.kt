package com.yjotdev.empprimaria.domain.entity

import com.google.gson.annotations.SerializedName

/**
 Modelo para enviar correos electronicos.
 **/
data class EmailEntity(
    @SerializedName("to") val to: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("text") val text: String
)