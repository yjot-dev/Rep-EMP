package com.yjotdev.empprimaria.presentation.mvvm.state

import com.yjotdev.empprimaria.domain.model.UserModel

/**
 Modelo para el estado del ProgressViewModel.
 **/
data class UiState(
    //Estados del usuario
    val experience: Int = 0,
    val timeSpent: Int = 0,
    val courseCompleted: Int = 0,
    val life: Int = 3,
    //Estados de consultas a BD
    val user: UserModel = UserModel(),
    //Estados operativos
    val isDialogDisplayed: Boolean = false,
    val isLoading: Boolean = false,
    val isBtnNextDisplayed: Boolean = false,
    val isTimerOn: Boolean = false,
    val progressLevel: Float = 0f,
    val currentLevelNum: Int = 0
)