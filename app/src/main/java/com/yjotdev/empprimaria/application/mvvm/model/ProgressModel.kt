package com.yjotdev.empprimaria.application.mvvm.model

import com.yjotdev.empprimaria.domain.entity.UserEntity

/**
 Modelo para el estado del ProgressViewModel.
 **/
data class ProgressModel(
    val experience: Int = 0,
    val timeSpent: Int = 0,
    val courseCompleted: Int = 0,
    val life: Int = 3,
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val wasFound: Boolean = false,
    val wasInserted: Boolean = false,
    val wasUpdated: Boolean = false,
    val wasDeleted: Boolean = false,
    val wasEmailed: Boolean = false,
    val operationCompletedCount: Int = 0
)