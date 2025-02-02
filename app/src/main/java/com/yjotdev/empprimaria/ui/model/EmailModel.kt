package com.yjotdev.empprimaria.ui.model

import com.google.gson.annotations.SerializedName

/**
 Modelo para enviar correos electronicos.
 **/
data class EmailModel(
    @SerializedName("to") val to: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("text") val text: String
)