package com.yjotdev.empprimaria.application.navigation

sealed class UiEvent {
    data class Navigate(
        val route: String,
        val routePopUp: String = ""
    ) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
    data class ShowLog(val message: String) : UiEvent()
}