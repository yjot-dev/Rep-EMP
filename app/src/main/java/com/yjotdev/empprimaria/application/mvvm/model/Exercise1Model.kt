package com.yjotdev.empprimaria.ui.model

/**
Modelo para usar en el ejercicio 1.
 **/
data class Exercise1Model(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)