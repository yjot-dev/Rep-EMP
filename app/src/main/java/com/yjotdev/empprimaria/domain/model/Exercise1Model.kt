package com.yjotdev.empprimaria.domain.model

/**
Modelo para usar en el ejercicio 1.
 **/
data class Exercise1Model(
    val question: Int = 0,
    val answer: List<Pair<Int, Boolean>> = listOf()
)