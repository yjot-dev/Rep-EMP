package com.yjotdev.empprimaria.presentation.mvvm.viewmodel

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
import com.yjotdev.empprimaria.presentation.mvvm.state.UiState
import com.yjotdev.empprimaria.presentation.navigation.UiEvent
import com.yjotdev.empprimaria.presentation.navigation.ViewRoutes
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase
import com.yjotdev.empprimaria.domain.usecase.user.SetPasswordRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.GetRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.GetLocalUserUseCase
import com.yjotdev.empprimaria.R
import kotlinx.coroutines.flow.firstOrNull

@HiltViewModel
class UiViewModel @Inject constructor(
    private val stringUseCase: StringUseCase,
    private val getRemoteUserUseCase: GetRemoteUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val insertRemoteUserUseCase: InsertRemoteUserUseCase,
    private val insertLocalUserUseCase: InsertLocalUserUseCase,
    private val updateRemoteUserUseCase: UpdateRemoteUserUseCase,
    private val updateLocalUserUseCase: UpdateLocalUserUseCase,
    private val setPasswordRemoteUserUseCase: SetPasswordRemoteUserUseCase,
    private val deleteRemoteUserUseCase: DeleteRemoteUserUseCase,
    private val deleteLocalUserUseCase: DeleteLocalUserUseCase,
    private val sendEmailUseCase: SendEmailUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    private val _eventChannel = Channel<UiEvent>()
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        checkExistingUser()
        observerFlowLocalUser()
    }

    override fun onCleared() {
        cleanState()
    }

    /** Limpia el estado del ViewModel **/
    fun cleanState(){
        _uiState.value = UiState()
    }

    /** Cambia estado del código aleatorio para el usuario **/
    fun setRandomCode(randomCode: Int) {
        _uiState.update { it.copy(randomCode = randomCode) }
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
    fun setUser(user: UserModel){
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
    fun setIsTimerOn(isTimerOff: Boolean){
        _uiState.update { state -> state.copy(isTimerOn = isTimerOff) }
    }

    /** Este metodo actualiza el estado de la variable progressLevel **/
    fun setProgressLevel(progressLevel: Float){
        _uiState.update { state -> state.copy(progressLevel = progressLevel) }
    }

    /** Este metodo actualiza el estado de la variable isBtnNextDisplayed **/
    fun setIsBtnNextDisplayed(isBtnNextDisplayed: Boolean){
        _uiState.update { it.copy(isBtnNextDisplayed = isBtnNextDisplayed) }
    }

    /** Este metodo cierra la sesión del usuario **/
    fun logoutUser(){
        viewModelScope.launch {
            deleteLocalUserUseCase(uiState.value.user)
            cleanState()
            _eventChannel.send(UiEvent.Navigate(
                route = ViewRoutes.Login.name,
                routePopUp = ViewRoutes.UserInfo.name
            ))
        }
    }

    /** Este metodo obtiene los datos del usuario en la BD **/
    fun loginUser(nameOrEmail: String, password: String){
        val login = LoginModel(
            name = nameOrEmail,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getRemoteUserUseCase(login)) {
                is Result.Success -> {
                    val user = result.data.copy(password = password)
                    insertLocalUserUseCase(user)
                    _uiState.update { it.copy(
                        isCompletedLogin = true,
                        isLoading = false
                    )}
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        isCompletedLogin = false,
                        isLoading = false
                    )}
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
            val userToInsert = UserModel(0, name, email, password)
            when (val result = insertRemoteUserUseCase(userToInsert)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.alert_user_registered))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.error_user_registered))
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
            val user = UserModel(id, name, email, password, photo)
            when (val result = updateRemoteUserUseCase(id, user)) {
                is Result.Success -> {
                    updateLocalUserUseCase(user)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.alert_user_updated))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.error_user_updated))
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
        val recovery = RecoveryModel(
            email = email,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            when (val result = setPasswordRemoteUserUseCase(recovery)) {
                is Result.Success -> {
                    val user = uiState.value.user.copy(password = password)
                    updateLocalUserUseCase(user)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.alert_user_changed_password))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.error_user_changed_password))
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
            when (val result = deleteRemoteUserUseCase(id)) {
                is Result.Success -> {
                    deleteLocalUserUseCase(uiState.value.user)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.alert_user_deleted))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.error_user_deleted))
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
            val email = EmailModel(to, subject, text)
            when (val result = sendEmailUseCase(email)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.alert_code_sent))
                    )
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        stringUseCase(R.string.error_code_sent))
                    )
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!)
                    )
                }
            }
        }
    }

    private fun checkExistingUser() {
        viewModelScope.launch {
            val user = getLocalUserUseCase().firstOrNull()
            if (user != null && user != UserModel()) {
                _eventChannel.send(UiEvent.Navigate(
                    route = ViewRoutes.UserInfo.name,
                    routePopUp = ViewRoutes.Login.name
                ))
            }
        }
    }

    private fun observerFlowLocalUser() {
        viewModelScope.launch {
            getLocalUserUseCase().collect { user ->
                _uiState.update { it.copy(user = user) }
                // Solo navega en login inicial
                if (uiState.value.user != UserModel() && uiState.value.isCompletedLogin) {
                    _uiState.update { it.copy(isCompletedLogin = false) }
                    _eventChannel.send(UiEvent.Navigate(
                        route = ViewRoutes.UserInfo.name,
                        routePopUp = ViewRoutes.Login.name
                    ))
                }
            }
        }
    }
}