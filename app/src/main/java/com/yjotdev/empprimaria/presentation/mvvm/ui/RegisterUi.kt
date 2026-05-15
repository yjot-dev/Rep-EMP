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
import kotlin.random.Random
import com.yjotdev.empprimaria.presentation.components.AlertDialogView
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TextFieldView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
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
    var isError1 by remember { mutableStateOf(false) }
    var isError2 by remember { mutableStateOf(false) }
    var isError3 by remember { mutableStateOf(false) }
    //Muestra el dialogo para enviar el codigo
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
                .focusRequester(focusRequest1),
            value = name,
            onValueChange = { name = it },
            onNext = { focusRequest2.requestFocus() },
            validateCase = 2,
            labelId = R.string.text_field_user,
            infoId = R.string.valid_user,
            onIsError = { isError1 = it }
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest2),
            value = email,
            onValueChange = { email = it },
            onNext = { focusRequest3.requestFocus() },
            validateCase = 3,
            labelId = R.string.text_field_email,
            infoId = R.string.valid_email,
            onIsError = { isError2 = it }
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest3),
            value = password,
            onValueChange = { password = it },
            imeAction = ImeAction.Done,
            validateCase = 5,
            labelId = R.string.text_field_password,
            infoId = R.string.valid_password,
            isPassword = true,
            onIsError = { isError3 = it }
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            click = {
                onSendCode(email, code)
                sendCode = true
            },
            enabled = !isError1 && !isError2 && !isError3 &&
                    name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty(),
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