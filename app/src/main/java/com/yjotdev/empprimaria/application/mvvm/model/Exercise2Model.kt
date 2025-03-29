package com.yjotdev.empprimaria.application.mvvm.model

/**
Modelo para usar en el ejercicio 2.
 **/
data class Exercise2Model(
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf()
)