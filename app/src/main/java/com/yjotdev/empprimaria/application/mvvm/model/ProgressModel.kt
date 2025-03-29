package com.yjotdev.empprimaria.application.mvvm.model

/**
 Modelo para el estado del ProgressViewModel.
 **/
data class ProgressModel(
    val experience: Int = 0,
    val timeSpent: Int = 0,
    val courseCompleted: Int = 0,
    val life: Int = 3,
)