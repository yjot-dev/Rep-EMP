package com.yjotdev.empprimaria.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.BackgroundView
import com.yjotdev.empprimaria.ui.view.utils.ButtonView
import com.yjotdev.empprimaria.ui.view.utils.TextFieldView

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit,
    onRecoverKey: () -> Unit,
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        BackgroundView(modifier = Modifier.fillMaxSize())
        ForegroundLogin(
            modifier = Modifier.fillMaxSize(),
            onLogin = onLogin,
            onRegister = onRegister,
            onRecoverKey = onRecoverKey
        )
    }
}

@Composable
private fun ForegroundLogin(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit,
    onRecoverKey: () -> Unit,
){
    val focusRequest1 = remember { FocusRequester() }
    val focusRequest2 = remember { FocusRequester() }
    var userOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.dm_8)),
            painter = painterResource(id = R.drawable.login_icon),
            contentDescription = stringResource(id = R.string.foreground_login),
            contentScale = ContentScale.Fit
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest1),
            value = userOrEmail,
            onValueChange = { userOrEmail = it },
            onNext = { focusRequest2.requestFocus() },
            validateCase = 1,
            labelId = R.string.text_field_user_email,
            infoId = R.string.valid_user_email
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest2),
            value = password,
            onValueChange = { password = it },
            imeAction = ImeAction.Done,
            validateCase = 5,
            isPassword = true,
            labelId = R.string.text_field_password,
            infoId = R.string.valid_password
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            click = { onLogin(userOrEmail, password) },
            text = stringResource(id = R.string.button_login)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            click = onRegister,
            text = stringResource(id = R.string.button_register)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            click = onRecoverKey,
            text = stringResource(id = R.string.button_recover_key)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewLoginView(){
    EmprendimientoPrimariaTheme{
        LoginView(
            modifier = Modifier.fillMaxSize(),
            onLogin = { _, _ -> },
            onRegister = {},
            onRecoverKey = {}
        )
    }
}