package com.yjotdev.empprimaria.ui.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.model.UserModel
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.AlertDialogView
import com.yjotdev.empprimaria.ui.view.utils.ButtonView
import com.yjotdev.empprimaria.ui.view.utils.TextFieldView
import com.yjotdev.empprimaria.ui.view.utils.convertToBitmap
import com.yjotdev.empprimaria.ui.view.utils.convertToBase64
import kotlin.random.Random

@Composable
fun UserInfoView(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    onLogout: () -> Unit,
    onUpdate: (String, String, String, String) -> Unit,
    onDelete: () -> Unit,
    onSendCode: (String, String) -> Unit
){
    val focusRequest1 = remember { FocusRequester() }
    val focusRequest2 = remember { FocusRequester() }
    val focusRequest3 = remember { FocusRequester() }
    var user by remember { mutableStateOf(userInfo.nombre) }
    var email by remember { mutableStateOf(userInfo.correo) }
    var password by remember { mutableStateOf(userInfo.clave) }
    var photo by remember { mutableStateOf(convertToBitmap(userInfo.foto)) }
    var sendCode by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }
    val code by remember { mutableStateOf(Random.nextInt(100000, 999999).toString()) }
    var isError1 by remember { mutableStateOf(false) }
    var isError2 by remember { mutableStateOf(false) }
    var isError3 by remember { mutableStateOf(false) }
    //Bloque asincronico para actualizar la foto
    val context = LocalContext.current
    var photoSelector by remember { mutableStateOf<Uri?>(null) }
    val launchPhotoSelector = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        uri: Uri? -> photoSelector = uri
    }
    LaunchedEffect(key1 = photoSelector) {
        photoSelector?.let {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(it)
                .build()
            val result = (loader.execute(request) as SuccessResult).drawable
            photo = (result.toBitmap()).copy(Bitmap.Config.ARGB_8888, true)
        }
    }
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
        if(photo == null)
            Image(
                painter = painterResource(id = R.drawable.login_icon),
                contentDescription = stringResource(id = R.string.image_user_info),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dm_8))
                    .clickable { launchPhotoSelector.launch("image/*") }
            )
        else
            Image(
                bitmap = photo!!.asImageBitmap(),
                contentDescription = stringResource(id = R.string.image_user_info),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dm_8))
                    .clickable { launchPhotoSelector.launch("image/*") }
            )
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest1),
            value = user,
            onValueChange = { user = it },
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
            click = onLogout,
            text = stringResource(id = R.string.button_logout)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = !isError2,
            click = {
                onSendCode(email, code)
                sendCode = true
            },
            text = stringResource(id = R.string.button_send_code)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = enabled && !isError1 && !isError2 && !isError3,
            click = { onUpdate(user, email, password, convertToBase64(photo)) },
            text = stringResource(id = R.string.button_update)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = enabled,
            click = onDelete,
            text = stringResource(id = R.string.button_delete)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewUserInfoView(){
    EmprendimientoPrimariaTheme {
        UserInfoView(
            modifier = Modifier.fillMaxSize(),
            userInfo = UserModel(),
            onLogout = {},
            onUpdate = {_, _, _, _ ->},
            onDelete = {},
            onSendCode = {_, _ ->}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewAlertDialog2(){
    EmprendimientoPrimariaTheme {
        AlertDialogView(
            onDismiss = {},
            onConfirm = {}
        )
    }
}