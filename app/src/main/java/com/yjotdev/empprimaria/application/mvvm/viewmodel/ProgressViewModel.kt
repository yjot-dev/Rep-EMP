package com.yjotdev.empprimaria.application.mvvm.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.receiveAsFlow
import com.yjotdev.empprimaria.application.mvvm.model.ProgressModel
import com.yjotdev.empprimaria.application.navigation.UiEvent
import com.yjotdev.empprimaria.application.navigation.ViewRoutes
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase
import com.yjotdev.empprimaria.R

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getString: StringUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val changePasswordUserUseCase: ChangePasswordUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val sendEmailUseCase: SendEmailUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ProgressModel())
    private val _eventChannel = Channel<UiEvent>()
    val uiState: StateFlow<ProgressModel> = _uiState.asStateFlow()
    val eventChannel = _eventChannel.receiveAsFlow()

    override fun onCleared() {
        super.onCleared()
        resetViewModel()
    }

    /** Este metodo actualiza el estado de la variable experience **/
    fun setExperience(experience: Int){
        _uiState.update { state ->
            state.copy(experience = experience)
        }
    }

    /** Este metodo actualiza el estado de la variable timeSpent **/
    fun setTimeSpent(timeSpent: Int){
        _uiState.update { state ->
            state.copy(timeSpent = timeSpent)
        }
    }

    /** Este metodo actualiza el estado de la variable courseCompleted **/
    fun setCourseCompleted(courseCompleted: Int){
        _uiState.update { state ->
            state.copy(courseCompleted = courseCompleted)
        }
    }

    /** Este metodo actualiza el estado de la variable life **/
    fun setLife(life: Int){
        _uiState.update { state ->
            state.copy(life = life)
        }
    }

    /** Este metodo actualiza el estado del usuario **/
    fun setUser(user: UserEntity){
        _uiState.update { state ->
            state.copy(user = user)
        }
    }

    /** Este metodo actualiza el estado de la variable currentLevelNum **/
    fun setCurrentLevelNum(currentLevelNum: Int){
        _uiState.update { state ->
            state.copy(currentLevelNum = currentLevelNum)
        }
    }

    /** Este metodo actualiza el estado de la variable isTimerOff **/
    fun setIsTimerOff(isTimerOff: Boolean){
        _uiState.update { state -> state.copy(isTimerOff = isTimerOff) }
    }

    /** Este metodo actualiza el estado de la variable progressLevel **/
    fun setProgressLevel(progressLevel: Float){
        _uiState.update { state -> state.copy(progressLevel = progressLevel) }
    }

    /** Este metodo actualiza el estado de la variable isDialogDisplayed **/
    fun setIsDialogDisplayed(isDialogDisplayed: Boolean){
        _uiState.update { it.copy(isDialogDisplayed = isDialogDisplayed) }
    }

    /** Este metodo actualiza el estado de la variable isBtnNextDisplayed **/
    fun setIsBtnNextDisplayed(isBtnNextDisplayed: Boolean){
        _uiState.update { it.copy(isBtnNextDisplayed = isBtnNextDisplayed) }
    }

    /** Este metodo cierra la sesión del usuario **/
    fun logoutUser(){
        viewModelScope.launch {
            _eventChannel.send(UiEvent.Navigate(
                route = ViewRoutes.Login.name,
                routePopUp = ViewRoutes.UserInfo.name
            ))
        }
    }

    /** Este metodo obtiene los datos del usuario en la BD **/
    fun loginUser(nameOrEmail: String, password: String){
        val login = LoginEntity(
            name = nameOrEmail,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = findUserUseCase(login)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = result.data.copy(
                                password = password
                            )
                        )
                    }
                    _eventChannel.send(UiEvent.Navigate(
                        route = ViewRoutes.UserInfo.name,
                        routePopUp = ViewRoutes.Login.name
                    ))
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = UserEntity()
                        )
                    }
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo ingresa los datos de un nuevo usuario en la BD **/
    fun insertUser(name: String, email: String, password: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val userToInsert = UserEntity(0, name, email, password)
            when (val result = insertUserUseCase(userToInsert)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.alert_user_registered))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.error_user_registered))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo actualiza los datos de un usuario existente en la BD **/
    fun updateUser(
        id: Int,
        name: String,
        email: String,
        password: String,
        photo: String
    ){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val userToUpdate = UserEntity(id, name, email, password, photo)
            when (val result = updateUserUseCase(id, userToUpdate)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.alert_user_updated))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.error_user_updated))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo cambia la clave del usuario de la BD **/
    fun changePassword(email: String, password: String){
        val recovery = RecoveryEntity(
            email = email,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            when (val result = changePasswordUserUseCase(recovery)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.alert_user_changed_password))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.error_user_changed_password))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo elimina un usuario de la BD **/
    fun deleteUser(id: Int){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            when (val result = deleteUserUseCase(id)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.alert_user_deleted))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.error_user_deleted))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo envia un correo al usuario con el código de verificación **/
    fun sendEmail(to: String, subject: String, text: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val email = EmailEntity(to, subject, text)
            when (val result = sendEmailUseCase(email)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.alert_code_sent))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.error_code_sent))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    /** Este metodo reinicia el estado del ProgressViewModel **/
    fun resetViewModel(){
        _uiState.value = ProgressModel()
    }
}