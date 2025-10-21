package com.yjotdev.empprimaria.domain.entity

/**
Modelo para usar en el ejercicio 1.
 **/
data class Exercise1Entity(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)