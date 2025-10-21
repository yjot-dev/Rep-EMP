package com.yjotdev.empprimaria.application.mvvm.viewmodel

import android.graphics.Bitmap
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
import com.yjotdev.empprimaria.application.utils.ImageUtils.convertToBitmap
import com.yjotdev.empprimaria.application.utils.ImageUtils.convertToBase64
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.usecase.email.SendCommentaryUseCase
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase
import com.yjotdev.empprimaria.domain.utils.data.Exercise1
import com.yjotdev.empprimaria.domain.utils.data.Exercise2
import com.yjotdev.empprimaria.domain.utils.data.Exercise3
import com.yjotdev.empprimaria.domain.utils.data.Projects
import com.yjotdev.empprimaria.domain.utils.data.Stories
import com.yjotdev.empprimaria.domain.utils.Validation

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
    val exercise1 = Exercise1.data
    val exercise2 = Exercise2.data
    val exercise3 = Exercise3.data
    val story = Stories.data
    val projectList = Projects.list

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

    /** Este metodo valida un texto ingresado **/
    fun getValidateText(text: String, case: Int): String =
        Validation.validateText(text, case)

    /** Este metodo convierte una foto en base64 a un objeto Bitmap **/
    fun getBitmap(): Bitmap? =
        convertToBitmap(userInfo.value.photo)

    /** Este metodo convierte un objeto Bitmap a una foto en base64 **/
    fun getBase64(): String =
        convertToBase64(getBitmap())

    fun clearFlags() {
        _uiState.update { state ->
            state.copy(wasFound = false, wasInserted = false,
                wasUpdated = false, wasDeleted = false, wasEmailed = false)
        }
    }

    /** Este metodo obtiene los datos del usuario en la BD **/
    fun findUser(name: String, email: String, password: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = findUserUseCase(name, email, password)
            when (result) {
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
            val result = insertUserUseCase(userToInsert)
            when (result) {
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
            val result = updateUserUseCase(id, userToUpdate)
            when (result) {
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
                            operationCompletedCount = it.operationCompletedCount + 1
                        )
                    }
                }
            }
        }
    }

    /** Este metodo cambia la clave del usuario de la BD **/
    fun changePassword(email: String, password: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch{
            val result = changePasswordUserUseCase(email, password)
            when (result) {
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
            val result = deleteUserUseCase(id)
            when (result) {
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
            val result = sendEmailUseCase(email)
            when (result) {
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
            val result = sendCommentaryUseCase(email)
            when (result) {
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