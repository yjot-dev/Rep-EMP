package com.yjotdev.empprimaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yjotdev.empprimaria.ui.model.ProgressModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProgressViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ProgressModel())
    val uiState: StateFlow<ProgressModel> = _uiState.asStateFlow()
    /**
    Este metodo actualiza el estado de la variable experience.
    **/
    fun setExperience(experience: Int){
        _uiState.update { state ->
            state.copy(experience = experience)
        }
    }
    /**
    Este metodo actualiza el estado de la variable timeSpent.
    **/
    fun setTimeSpent(timeSpent: Int){
        _uiState.update { state ->
            state.copy(timeSpent = timeSpent)
        }
    }
    /**
    Este metodo actualiza el estado de la variable courseCompleted.
    **/
    fun setCourseCompleted(courseCompleted: Int){
        _uiState.update { state ->
            state.copy(courseCompleted = courseCompleted)
        }
    }
    /**
    Este metodo actualiza el estado de la variable life.
    **/
    fun setLife(life: Int){
        _uiState.update { state ->
            state.copy(life = life)
        }
    }
    /**
    Este metodo reinicia el estado del ProgressViewModel.
    **/
    fun reset(){
        _uiState.value = ProgressModel()
    }
}