package com.yjotdev.empprimaria.ui.viewmodel

import android.content.Context
import android.widget.Toast
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.api_client.UserClient
import com.yjotdev.empprimaria.ui.model.EmailModel
import com.yjotdev.empprimaria.ui.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserController {
    private val userClient by lazy{ UserClient() }
    /**
    Este metodo obtiene los datos del usuario en la BD.
     **/
    fun login(
        context: Context,
        userOrEmail: String,
        password: String,
        callback: (UserModel) -> Unit
    ){
        val userToFind = UserModel(0, userOrEmail, userOrEmail, password)
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.findUser(userToFind)
            withContext(Dispatchers.Main) {
                if (result != null) {
                    callback(result)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_user_login),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /**
    Este metodo ingresa los datos de un nuevo usuario en la BD.
     **/
    fun register(
        context: Context,
        user: String,
        email: String,
        password: String
    ){
        val userToInsert = UserModel(0, user, email, password)
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.insertUser(userToInsert)
            withContext(Dispatchers.Main) {
                if (result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_user_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /**
    Este metodo actualiza los datos de un usuario existente en la BD.
     **/
    fun update(
        context: Context,
        id: Int,
        user: String,
        email: String,
        password: String,
        photo: String
    ){
        val userToUpdate = UserModel(id, user, email, password, photo)
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.updateUser(id, userToUpdate)
            withContext(Dispatchers.Main){
                if (result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_user_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /**
    Este metodo elimina un usuario de la BD.
     **/
    fun delete(
        context: Context,
        id: Int
    ){
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.deleteUser(id)
            withContext(Dispatchers.Main){
                if (result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_user_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /**
    Este metodo envia un correo al usuario con el código de verificación.
    **/
    fun sendCodeByEmail(
        context: Context,
        to: String,
        name: String,
        code: String
    ){
        val subject = context.getString(R.string.alert_dialog_code)
        val text = context.getString(R.string.body_email, name, code)
        val email = EmailModel(to, subject, text)
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.sendEmail(email)
            withContext(Dispatchers.Main){
                if (result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /**
    Este metodo envia un comentario del usuario al correo de la empresa.
    **/
    fun sendCommentaryByEmail(
        context: Context,
        text: String,
        name: String,
    ){
        val subject = context.getString(R.string.alert_dialog_opinion, name)
        val email = EmailModel("", subject, text)
        CoroutineScope(Dispatchers.IO).launch {
            val result = userClient.sendCommentary(email)
            withContext(Dispatchers.Main){
                if (result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}