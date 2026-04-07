package com.yjotdev.empprimaria.domain.entity

/**
Modelo para usar en el ejercicio 2.
 **/
data class Exercise2Entity(
    val question: Int = 0,
    val answer: List<Pair<Int, Boolean>> = listOf()
)