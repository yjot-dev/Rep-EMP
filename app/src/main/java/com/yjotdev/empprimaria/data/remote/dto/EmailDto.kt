package com.yjotdev.empprimaria.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmailDto(
    @SerializedName("para") val to: String = "",
    @SerializedName("asunto") val subject: String = "",
    @SerializedName("mensaje") val text: String = ""
)