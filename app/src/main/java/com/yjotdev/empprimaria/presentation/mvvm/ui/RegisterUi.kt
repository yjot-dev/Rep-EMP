package com.yjotdev.empprimaria.presentation.mvvm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.testTag
import kotlin.random.Random
import com.yjotdev.empprimaria.presentation.utils.Helper
import com.yjotdev.empprimaria.presentation.components.AlertDialogView
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TextFieldView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.TestTags
import com.yjotdev.empprimaria.R

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    onSendCode: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
){
    val focusRequest1 = remember { FocusRequester() }
    val focusRequest2 = remember { FocusRequester() }
    val focusRequest3 = remember { FocusRequester() }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var sendCode by remember { mutableStateOf(false) }
    val code by remember { mutableStateOf(Random.nextInt(100000, 999999).toString()) }
    val isValidUser = Helper.isValidUser(name)
    val isValidEmail = Helper.isValidEmail(email)
    val isValidPassword = Helper.isValidPassword(password)
    //Muestra el diálogo para enviar el código
    if(sendCode){
        AlertDialogView(
            onDismiss = { sendCode = false },
            onConfirm = { codeIn ->
                if(code == codeIn){
                    onRegister(name, email, password)
                    sendCode = false
                }
            }
        )
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_5)))
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest1)
                .testTag(TestTags.REGISTER_USER_FIELD),
            value = name,
            onValueChange = { name = it },
            onNext = { focusRequest2.requestFocus() },
            validateCase = isValidUser,
            labelId = R.string.text_field_user,
            infoId = R.string.valid_user
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest2)
                .testTag(TestTags.REGISTER_EMAIL_FIELD),
            value = email,
            onValueChange = { email = it },
            onNext = { focusRequest3.requestFocus() },
            validateCase = isValidEmail,
            labelId = R.string.text_field_email,
            infoId = R.string.valid_email
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest3)
                .testTag(TestTags.REGISTER_PASSWORD_FIELD),
            value = password,
            onValueChange = { password = it },
            imeAction = ImeAction.Done,
            validateCase = isValidPassword,
            labelId = R.string.text_field_password,
            infoId = R.string.valid_password,
            isPassword = true
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.REGISTER_SUBMIT_BUTTON),
            click = {
                onSendCode(email, code)
                sendCode = true
            },
            enabled = isValidUser && isValidEmail && isValidPassword,
            text = stringResource(id = R.string.button_create_user)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_5)))
    }
}

@ComponentPreview
@Composable
private fun PreviewRegisterView(){
    EmprendimientoPrimariaTheme{
        RegisterView(
            modifier = Modifier.fillMaxSize(),
            onSendCode = { _, _ -> },
            onRegister = { _, _, _ -> }
        )
    }
}