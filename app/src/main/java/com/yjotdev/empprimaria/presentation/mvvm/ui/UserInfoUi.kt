package com.yjotdev.empprimaria.presentation.mvvm.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlin.random.Random
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.AlertDialogView
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TextFieldView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.Helper.convertToBase64
import com.yjotdev.empprimaria.presentation.utils.Helper.convertToBitmap
import com.yjotdev.empprimaria.domain.model.UserModel

@Composable
fun UserInfoView(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    isDialogDisplayed: Boolean,
    onIsDialogDisplayed: (Boolean) -> Unit,
    onUserInfo: (Int, String) -> Unit,
    onLogout: () -> Unit,
    onUpdate: (String, String, String, String) -> Unit,
    onDelete: () -> Unit,
    onSendCode: (String, String) -> Unit
){
    val focusRequest1 = remember { FocusRequester() }
    val focusRequest2 = remember { FocusRequester() }
    val focusRequest3 = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    var photo by remember { mutableStateOf(convertToBitmap(userInfo.photo)) }
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
    if(isDialogDisplayed){
        AlertDialogView(
            onDismiss = { onIsDialogDisplayed(false) },
            onConfirm = { codeIn ->
                if(code == codeIn){
                    enabled = true
                    onIsDialogDisplayed(false)
                }
            }
        )
    }
    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(onClick = { launchPhotoSelector.launch("image/*") }) {
            if(photo == null) {
                Icon(
                    painter = painterResource(id = R.drawable.login_icon),
                    contentDescription = stringResource(id = R.string.image_user_info),
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    bitmap = photo!!.asImageBitmap(),
                    contentDescription = stringResource(id = R.string.image_user_info),
                    tint = Color.Unspecified
                )
            }
        }
        TextFieldView(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .focusRequester(focusRequest1),
            value = userInfo.name,
            onValueChange = { name -> onUserInfo(1, name) },
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
            value = userInfo.email,
            onValueChange = { email -> onUserInfo(2, email) },
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
            value = userInfo.password,
            onValueChange = { password -> onUserInfo(3, password) },
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
                onSendCode(userInfo.email, code)
                onIsDialogDisplayed(true)
            },
            text = stringResource(id = R.string.button_send_code)
        )
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = enabled && !isError1 && !isError2 && !isError3,
            click = { onUpdate(userInfo.name, userInfo.email, userInfo.password, convertToBase64(photo)) },
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

@ComponentPreview
@Composable
private fun PreviewUserInfoView(){
    EmprendimientoPrimariaTheme {
        UserInfoView(
            modifier = Modifier.fillMaxSize(),
            userInfo = UserModel(
                name = "Yasser",
                email = "2010guabo@gmail.com",
                password = "Test1000",
                photo = ""
            ),
            isDialogDisplayed = false,
            onIsDialogDisplayed = {},
            onUserInfo = {_,_ ->},
            onLogout = {},
            onUpdate = {_, _, _, _ ->},
            onDelete = {},
            onSendCode = {_, _ ->}
        )
    }
}

@ComponentPreview
@Composable
private fun PreviewAlertDialog2(){
    EmprendimientoPrimariaTheme {
        AlertDialogView(
            onDismiss = {},
            onConfirm = {}
        )
    }
}