package com.yjotdev.empprimaria.application.mvvm.view

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
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.application.components.BackgroundView

@Composable
fun MenuView(
    modifier: Modifier = Modifier,
    myName: String,
    myEmail: String,
    myPassword: String,
    myPhoto: String,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    myLife: Int,
    isPreview: Boolean = false,
    progressLevel: Float = 0f,
    onProgressLevel: (Float) -> Unit = {},
    isVisible: Boolean = false,
    onIsVisible: (Boolean) -> Unit = {},
    onIsTimerOff: (Boolean) -> Unit = {},
    onLogout: () -> Unit = {},
    onUpdate: (String, String, String, String) -> Unit = {_, _, _, _ ->},
    onDelete: () -> Unit = {},
    onSendCode: (String, String) -> Unit = {_,_ ->},
    onSendOpinion: (String) -> Unit = {},
    onCallback: (Int, Boolean?) -> Unit = {_,_ ->}
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        BackgroundView(modifier = Modifier.fillMaxSize())
        ForegroundMenu(
            modifier = Modifier.fillMaxSize(),
            myName = myName,
            myEmail = myEmail,
            myPassword = myPassword,
            myPhoto = myPhoto,
            myExperience = myExperience,
            myTimeSpent = myTimeSpent,
            myCourseCompleted = myCourseCompleted,
            myLife = myLife,
            onLogout = onLogout,
            onUpdate = onUpdate,
            onDelete = onDelete,
            onSendCode = onSendCode,
            isPreview = isPreview,
            progressLevel = progressLevel,
            onProgressLevel = onProgressLevel,
            isVisible = isVisible,
            onIsVisible = onIsVisible,
            onIsTimerOff = onIsTimerOff,
            onSendOpinion = onSendOpinion,
            onCallback = onCallback
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
    myName: String,
    myEmail: String,
    myPassword: String,
    myPhoto: String,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    myLife: Int,
    isPreview: Boolean,
    progressLevel: Float = 0f,
    onProgressLevel: (Float) -> Unit,
    isVisible: Boolean,
    onIsVisible: (Boolean) -> Unit,
    onIsTimerOff: (Boolean) -> Unit,
    onLogout: () -> Unit,
    onUpdate: (String, String, String, String) -> Unit,
    onDelete: () -> Unit,
    onSendCode: (String, String) -> Unit,
    onSendOpinion: (String) -> Unit,

    onCallback: (Int, Boolean?) -> Unit
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
                myName = myName,
                myEmail = myEmail,
                myPassword = myPassword,
                myPhoto = myPhoto,
                onLogout = onLogout,
                onUpdate = onUpdate,
                onDelete = onDelete,
                onSendCode = onSendCode
            )
            1 -> UnitsView(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                myLife = myLife,
                progressLevel = progressLevel,
                onProgressLevel = onProgressLevel,
                isVisible = isVisible,
                onIsVisible = onIsVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = onCallback
            )
            2 -> ProjectListView(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                isPreview = isPreview
            )
            3 -> OpinionView(
                modifier = Modifier.fillMaxSize(),
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
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
            myName = "Juan",
            myEmail = "juan@gmail.com",
            myPassword = "Test2000",
            myPhoto = "",
            myExperience = 0,
            myTimeSpent = 0,
            myCourseCompleted = 0,
            myLife = 3,
            isPreview = true
        )
    }
}