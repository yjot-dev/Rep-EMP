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
    val user: UserEntity? = null,
    val wasFound: Boolean = false,
    val wasInserted: Boolean = false,
    val wasUpdated: Boolean = false,
    val wasDeleted: Boolean = false,
    val wasEmailed: Boolean = false,
    //Estados operativos
    val error: String? = null,
    val isLoading: Boolean = false,
    val operationCompletedCount: Int = 0,
    val progressLevel: Float = 0f,
    val isDialogVisible: Boolean = false,
    val isTimerOff: Boolean = false,
    val currentLevelNum: Int = 0,
    val currentOperationId: Int = 0
)