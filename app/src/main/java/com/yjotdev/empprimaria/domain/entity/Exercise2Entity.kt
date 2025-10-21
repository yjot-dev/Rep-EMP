package com.yjotdev.empprimaria.domain.entity

/**
Modelo para usar en el ejercicio 2.
 **/
data class Exercise2Entity(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)