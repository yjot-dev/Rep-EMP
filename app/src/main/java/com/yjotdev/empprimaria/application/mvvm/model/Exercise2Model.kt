package com.yjotdev.empprimaria.ui.model

/**
Modelo para usar en el ejercicio 2.
 **/
data class Exercise2Model(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)