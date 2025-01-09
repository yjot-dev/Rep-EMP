package com.yjotdev.empprimaria.ui.view.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.yjotdev.empprimaria.R

@Composable
fun BackgroundView(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.background_login),
        contentDescription = stringResource(id = R.string.background_login),
        contentScale = ContentScale.FillHeight,
        alpha = 0.5f
    )
}

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    click: () -> Unit,
    text: String
){
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.dm_2),
            color = MaterialTheme.colorScheme.secondary
        ),
        onClick = click
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun TextFieldView(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    onNext: () -> Unit = {},
    validateCase: Int = 0,
    @StringRes labelId: Int,
    @StringRes infoId: Int
){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    TextField(
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.labelLarge
            )
        },
        visualTransformation =
        if(isPassword){
            if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        }else{
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible)
                    ImageVector.vectorResource(id = R.drawable.show_password)
                else
                    ImageVector.vectorResource(id = R.drawable.hide_password)
                val description = if (passwordVisible)
                    stringResource(id = R.string.show_password)
                else
                    stringResource(id = R.string.hide_password)
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ){
                    Icon(
                        imageVector = image,
                        contentDescription = description
                    )
                }
            }
        },
        value = value,
        onValueChange = { text ->
            onValueChange(text)
            errorMessage = validateText(context, text, validateCase)
        },
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            Text(errorMessage.ifEmpty { stringResource(id = infoId) })
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { onNext() },
            onDone = { keyboardController?.hide() }
        ),
        maxLines = maxLines
    )
}

@Composable
fun AlertDialogView(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
){
    var code by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.alert_dialog_code),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            TextFieldView(
                modifier = Modifier.fillMaxWidth(),
                value = code,
                onValueChange = { code = it },
                labelId = R.string.text_field_code,
                infoId = R.string.valid_number,
                validateCase = 4,
                imeAction = ImeAction.Done
            )
        },
        confirmButton = {
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(),
                click = { onConfirm(code) },
                text = stringResource(id = R.string.button_verify_code)
            )
        }
    )
}

@Composable
fun TitleView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = ""
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            )
            if(subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

@Composable
fun TextView(
    modifier: Modifier = Modifier,
    text: String
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Column{
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        }
    }
}