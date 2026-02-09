package com.yjotdev.empprimaria.application.mvvm.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.yjotdev.empprimaria.application.mvvm.model.ProgressModel
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity
import com.yjotdev.empprimaria.domain.usecase.email.SendCommentaryUseCase
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val findUserUseCase: FindUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val changePasswordUserUseCase: ChangePasswordUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val sendCommentaryUseCase: SendCommentaryUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ProgressModel())
    private val _userInfo = MutableStateFlow(UserEntity())
    val uiState: StateFlow<ProgressModel> = _uiState.asStateFlow()
    val userInfo: StateFlow<UserEntity> = _userInfo.asStateFlow()

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

    /** Este metodo actualiza el estado del objeto UserInfo **/
    fun setUserInfo(userInfo: UserEntity){
        _userInfo.update { state ->
            state.copy(
                id = userInfo.id,
                name = userInfo.name,
                email = userInfo.email,
                password = userInfo.password,
                photo = userInfo.photo
            )
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

    /** Este metodo actualiza el estado de la variable currentLevelNum **/
    fun setProgressLevel(progressLevel: Float){
        _uiState.update { state -> state.copy(progressLevel = progressLevel) }
    }

    /** Este metodo actualiza el estado de la variable isAnswerDialogVisible **/
    fun setDialogVisible(isDialogVisible: Boolean){
        _uiState.update { it.copy(isDialogVisible = isDialogVisible) }
    }

    /** Este metodo actualiza el estado de la variable currentOperationId **/
    fun setCurrentOperationId(currentOperationId: Int){
        _uiState.update { it.copy(currentOperationId = currentOperationId) }
    }

    /** Limpia los flags **/
    fun clearFlags(){
        _uiState.update { state ->
            state.copy(wasFound = false, wasInserted = false,
                wasUpdated = false, wasDeleted = false, wasEmailed = false)
        }
    }

    /** Este metodo obtiene los datos del usuario en la BD **/
    fun findUser(nameOrEmail: String, password: String){
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
                            user = result.data,
                            wasFound = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = null,
                            wasFound = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
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
                        it.copy(
                            isLoading = false,
                            wasInserted = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasInserted = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
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
                        it.copy(
                            isLoading = false,
                            wasUpdated = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
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
                        it.copy(
                            isLoading = false,
                            wasUpdated = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
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
                        it.copy(
                            isLoading = false,
                            wasDeleted = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasDeleted = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
            }
        }
    }

    /** Este metodo envia un correo al usuario con el código de verificación **/
    fun sendCodeByEmail(to: String, subject: String, text: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val email = EmailEntity(to, subject, text)
            when (val result = sendEmailUseCase(email)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
            }
        }
    }

    /** Este metodo envia un comentario del usuario al correo de la empresa **/
    fun sendCommentaryByEmail(subject: String, text: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val email = EmailEntity("", subject, text)
            when (val result = sendCommentaryUseCase(email)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = true,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = false,
                            error = result.exception.message,
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
            }
        }
    }

    /** Este metodo reinicia el estado del ProgressViewModel **/
    fun resetViewModel(){
        _uiState.value = ProgressModel()
        _userInfo.value = UserEntity()
    }
}