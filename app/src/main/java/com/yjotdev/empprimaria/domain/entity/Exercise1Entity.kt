package com.yjotdev.empprimaria.domain.entity

/**
Modelo para usar en el ejercicio 1.
 **/
data class Exercise1Entity(
    val question: Int = 0,
    val answer: List<Pair<Int, Boolean>> = listOf()
)