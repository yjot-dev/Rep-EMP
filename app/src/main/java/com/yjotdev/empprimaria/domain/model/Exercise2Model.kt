package com.yjotdev.empprimaria.domain.model

/**
Modelo para usar en el ejercicio 2.
 **/
data class Exercise2Model(
    val question: Int = 0,
    val answer: List<Pair<Int, Boolean>> = listOf()
)