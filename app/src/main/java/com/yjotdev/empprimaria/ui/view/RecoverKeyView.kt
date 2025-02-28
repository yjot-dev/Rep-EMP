package com.yjotdev.empprimaria.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.BackgroundView
import com.yjotdev.empprimaria.ui.view.utils.AlertDialogView
import com.yjotdev.empprimaria.ui.view.utils.ButtonView
import com.yjotdev.empprimaria.ui.view.utils.TextFieldView
import kotlin.random.Random

@Composable
fun RecoverKeyView(
    modifier: Modifier = Modifier,
    onChangePassword: (String, String) -> Unit,
    onSendCode: (String, String) -> Unit
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        BackgroundView(modifier = Modifier.fillMaxSize())
        ForegroundRecoverKey(
            modifier = Modifier.fillMaxSize(),
            onChangePassword = onChangePassword,
            onSendCode = onSendCode
        )
    }
}

@Composable
private fun ForegroundRecoverKey(
    modifier: Modifier = Modifier,
    onChangePassword: (String, String) -> Unit,
    onSendCode: (String, String) -> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var sendCode by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }
    val code by remember { mutableStateOf(Random.nextInt(100000, 999999).toString()) }
    var isError1 by remember { mutableStateOf(false) }
    var isError2 by remember { mutableStateOf(false) }
    //Muestra el dialogo para enviar el codigo
    if(sendCode){
        AlertDialogView(
            onDismiss = { sendCode = false },
            onConfirm = { codeIn ->
                if(code == codeIn){
                    enabled = true
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
            modifier = Modifier.fillMaxWidth(0.85f),
            value = email,
            onValueChange = { email = it },
            validateCase = 3,
            labelId = R.string.text_field_email,
            infoId = R.string.valid_email,
            onIsError = { isError1 = it }
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            click = {
                onSendCode(email, code)
                sendCode = true
            },
            enabled = !isError1,
            text = stringResource(id = R.string.button_send_code)
        )
        TextFieldView(
            modifier = Modifier.fillMaxWidth(0.85f),
            enabled = enabled,
            value = password,
            onValueChange = { password = it },
            validateCase = 5,
            labelId = R.string.text_field_password_new,
            infoId = R.string.valid_password,
            onIsError = { isError2 = it }
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = enabled && !isError2,
            click = { onChangePassword(email, password) },
            text = stringResource(id = R.string.button_change_password)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_5)))
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewRecoverKeyView(){
    EmprendimientoPrimariaTheme {
        RecoverKeyView(
            modifier = Modifier.fillMaxSize(),
            onChangePassword = {_, _ ->},
            onSendCode = {_, _ ->}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewAlertDialog1(){
    EmprendimientoPrimariaTheme {
        AlertDialogView(
            onDismiss = {},
            onConfirm = {}
        )
    }
}