package com.yjotdev.empprimaria.domain.entity

/**
 Modelo para enviar correos electronicos.
 **/
data class EmailEntity(
    val to: String,
    val subject: String,
    val text: String
)