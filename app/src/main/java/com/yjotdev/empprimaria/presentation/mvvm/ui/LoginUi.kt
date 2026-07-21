package com.yjotdev.empprimaria.presentation.mvvm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TextFieldView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.Helper
import com.yjotdev.empprimaria.presentation.utils.TestTags
import com.yjotdev.empprimaria.R

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit,
    onRecoverKey: () -> Unit,
){
    val focusRequest1 = remember { FocusRequester() }
    val focusRequest2 = remember { FocusRequester() }
    var nameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isValidUserOrEmail = Helper.isValidUserOrEmail(nameOrEmail)
    val isValidPassword = Helper.isValidPassword(password)
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
                .focusRequester(focusRequest1)
                .testTag(TestTags.LOGIN_USER_EMAIL_FIELD),
            value = nameOrEmail,
            onValueChange = { nameOrEmail = it },
            onNext = { focusRequest2.requestFocus() },
            validateCase = isValidUserOrEmail,
            labelId = R.string.text_field_user_email,
            infoId = R.string.valid_user_email
        )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest2)
                .testTag(TestTags.LOGIN_PASSWORD_FIELD),
            value = password,
            onValueChange = { password = it },
            imeAction = ImeAction.Done,
            validateCase = isValidPassword,
            isPassword = true,
            labelId = R.string.text_field_password,
            infoId = R.string.valid_password
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.LOGIN_SUBMIT_BUTTON),
            click = { onLogin(nameOrEmail, password) },
            enabled = isValidUserOrEmail && isValidPassword,
            text = stringResource(id = R.string.button_login)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.LOGIN_REGISTER_BUTTON),
            click = onRegister,
            text = stringResource(id = R.string.button_register)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.LOGIN_RECOVER_KEY_BUTTON),
            click = onRecoverKey,
            text = stringResource(id = R.string.button_recover_key)
        )
    }
}

@ComponentPreview
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