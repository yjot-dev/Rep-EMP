package com.yjotdev.empprimaria.application.mvvm.model

import com.yjotdev.empprimaria.domain.entity.UserEntity

/**
 Modelo para el estado del ProgressViewModel.
 **/
data class ProgressModel(
    //Estados del usuario
    val experience: Int = 0,
    val timeSpent: Int = 0,
    val courseCompleted: Int = 0,
    val life: Int = 3,
    //Estados de consultas a BD
    val user: UserEntity = UserEntity(),
    //Estados operativos
    val isDialogDisplayed: Boolean = false,
    val isLoading: Boolean = false,
    val isBtnNextDisplayed: Boolean = false,
    val isTimerOn: Boolean = false,
    val progressLevel: Float = 0f,
    val currentLevelNum: Int = 0
)