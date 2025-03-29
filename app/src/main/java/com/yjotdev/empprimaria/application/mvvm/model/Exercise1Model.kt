package com.yjotdev.empprimaria.application.mvvm.model

/**
Modelo para usar en el ejercicio 1.
 **/
data class Exercise1Model(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)