package com.yjotdev.empprimaria.ui.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.model.UserModel
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.BackgroundView
import com.yjotdev.empprimaria.ui.viewmodel.ProgressViewModel

@Composable
fun MenuView(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    userInfo: UserModel,
    onLogout: () -> Unit,
    onUpdate: (String, String, String, String) -> Unit,
    onDelete: () -> Unit,
    onSendCode: (String, String) -> Unit,
    isPreview: Boolean = false,
    onSendOpinion: (String) -> Unit
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        BackgroundView(modifier = Modifier.fillMaxSize())
        ForegroundMenu(
            modifier = Modifier.fillMaxSize(),
            progressVm = progressVm,
            userInfo = userInfo,
            onLogout = onLogout,
            onUpdate = onUpdate,
            onDelete = onDelete,
            onSendCode = onSendCode,
            isPreview = isPreview,
            onSendOpinion = onSendOpinion
        )
    }
}

@Composable
private fun MenuBar(
    modifier: Modifier = Modifier,
    selector: Int,
    clickUserInfo: () -> Unit,
    clickEducationalModules: () -> Unit,
    clickPracticalProjects: () -> Unit,
    clickTrackingAndSupport: () -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.user_info),
            contentDescription = stringResource(R.string.button_user_info),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dm_5))
                .background(if(selector == 0) MaterialTheme.colorScheme.inversePrimary
                            else Color.Unspecified)
                .clickable { clickUserInfo() }
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.educational_modules),
            contentDescription = stringResource(R.string.button_educational_modules),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dm_5))
                .background(if(selector == 1) MaterialTheme.colorScheme.inversePrimary
                            else Color.Unspecified)
                .clickable { clickEducationalModules() }
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.practical_projects),
            contentDescription = stringResource(R.string.button_practical_projects),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dm_5))
                .background(if(selector == 2) MaterialTheme.colorScheme.inversePrimary
                            else Color.Unspecified)
                .clickable { clickPracticalProjects() }
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.tracking_and_support),
            contentDescription = stringResource(R.string.button_tracking_and_support),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dm_5))
                .background(if(selector == 3) MaterialTheme.colorScheme.inversePrimary
                            else Color.Unspecified)
                .clickable { clickTrackingAndSupport() }
        )
    }
}

@Composable
private fun ForegroundMenu(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    userInfo: UserModel,
    onLogout: () -> Unit,
    onUpdate: (String, String, String, String) -> Unit,
    onDelete: () -> Unit,
    onSendCode: (String, String) -> Unit,
    isPreview: Boolean,
    onSendOpinion: (String) -> Unit
){
    var selector by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        MenuBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dm_5))
                .background(MaterialTheme.colorScheme.primaryContainer),
            selector = selector,
            clickUserInfo = { selector = 0 },
            clickEducationalModules = { selector = 1 },
            clickPracticalProjects = { selector = 2 },
            clickTrackingAndSupport = { selector = 3 }
        )
        when(selector){
            0 -> UserInfoView(
                modifier = Modifier.fillMaxSize(),
                userInfo = userInfo,
                onLogout = onLogout,
                onUpdate = onUpdate,
                onDelete = onDelete,
                onSendCode = onSendCode
            )
            1 -> UnitsView(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                progressVm = progressVm
            )
            2 -> ProjectListView(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                isPreview = isPreview
            )
            3 -> OpinionView(
                modifier = Modifier.fillMaxSize(),
                progressVm = progressVm,
                onSendOpinion = onSendOpinion
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewMenuView(){
    EmprendimientoPrimariaTheme {
        MenuView(
            modifier = Modifier.fillMaxSize(),
            progressVm = ProgressViewModel(),
            userInfo = UserModel(
                nombre = "Yasser",
                correo = "2010guabo@gmail.com",
                clave = "Yjot1997"
            ),
            onLogout = {},
            onUpdate = {_, _, _, _ ->},
            onDelete = {},
            onSendCode = {_, _ ->},
            isPreview = true,
            onSendOpinion = {}
        )
    }
}