package com.yjotdev.empprimaria.domain.model

data class EmailModel(
    val to: String = "",
    val subject: String = "",
    val text: String = ""
)