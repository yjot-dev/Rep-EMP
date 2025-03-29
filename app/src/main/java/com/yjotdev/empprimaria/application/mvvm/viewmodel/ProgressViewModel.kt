package com.yjotdev.empprimaria.application.mvvm.viewmodel

import android.graphics.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.yjotdev.empprimaria.application.mvvm.model.ProgressModel
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
                nombre = userInfo.nombre,
                correo = userInfo.correo,
                clave = userInfo.clave,
                foto = userInfo.foto
            )
        }
    }

    /** Este metodo valida un texto ingresado **/
    fun getValidateText(text: String, case: Int): String =
        Validation.validateText(text, case)

    /** Este metodo convierte una foto en base64 a un objeto Bitmap **/
    fun getBitmap(): Bitmap? =
        Validation.convertToBitmap(userInfo.value.foto)

    /** Este metodo convierte un objeto Bitmap a una foto en base64 **/
    fun getBase64(): String =
        Validation.convertToBase64(getBitmap())

    /** Este metodo obtiene los datos del usuario en la BD **/
    fun findUser(
        userOrEmail: String,
        password: String,
        result: (UserEntity) -> Unit
    ){
        viewModelScope.launch {
            val user = try{
                val userToFind = UserEntity(0, userOrEmail, userOrEmail, password)
                withContext(Dispatchers.IO) { findUserUseCase(userToFind) }
            }catch(e: Exception){
                UserEntity()
            }
            result(user)
        }
    }

    /** Este metodo ingresa los datos de un nuevo usuario en la BD **/
    fun insertUser(
        user: String,
        email: String,
        password: String,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                val userToInsert = UserEntity(0, user, email, password)
                withContext(Dispatchers.IO) { insertUserUseCase(userToInsert) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo actualiza los datos de un usuario existente en la BD **/
    fun updateUser(
        id: Int,
        user: String,
        email: String,
        password: String,
        photo: String,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                val userToUpdate = UserEntity(id, user, email, password, photo)
                withContext(Dispatchers.IO) { updateUserUseCase(id, userToUpdate) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo cambia la clave del usuario de la BD **/
    fun changePassword(
        email: String,
        password: String,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                val passwordUserToChange = UserEntity(0, "", email, password)
                withContext(Dispatchers.IO) { changePasswordUserUseCase(passwordUserToChange) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo elimina un usuario de la BD **/
    fun deleteUser(
        id: Int,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                withContext(Dispatchers.IO) { deleteUserUseCase(id) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo envia un correo al usuario con el código de verificación **/
    fun sendCodeByEmail(
        to: String,
        subject: String,
        text: String,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                val email = EmailEntity(to, subject, text)
                withContext(Dispatchers.IO) { sendEmailUseCase(email) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo envia un comentario del usuario al correo de la empresa **/
    fun sendCommentaryByEmail(
        subject: String,
        text: String,
        result: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try{
                val email = EmailEntity("", subject, text)
                withContext(Dispatchers.IO) { sendCommentaryUseCase(email) }
                result(true)
            }catch(e: Exception){
                result(false)
            }
        }
    }

    /** Este metodo reinicia el estado del ProgressViewModel **/
    fun reset(){
        _uiState.value = ProgressModel()
        _userInfo.value = UserEntity()
    }
}