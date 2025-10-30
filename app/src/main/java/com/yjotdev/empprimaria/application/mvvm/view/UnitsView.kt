package com.yjotdev.empprimaria.application.mvvm.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.domain.utils.data.Exercise1
import com.yjotdev.empprimaria.domain.utils.data.Exercise2
import com.yjotdev.empprimaria.domain.utils.data.Exercise3
import com.yjotdev.empprimaria.domain.utils.data.Stories
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.application.components.ButtonView
import com.yjotdev.empprimaria.application.components.StoryView
import com.yjotdev.empprimaria.application.components.TitleView

@Composable
fun UnitsView(
    modifier: Modifier = Modifier,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    myLife: Int,
    progressLevel: Float = 0f,
    onProgressLevel: (Float) -> Unit = {},
    isVisible: Boolean = false,
    onIsVisible: (Boolean) -> Unit = {},
    onIsTimerOff: (Boolean) -> Unit = {},
    onCallback: (Int, Boolean?) -> Unit = {_,_ ->}
){
    var level by remember { mutableStateOf("0") }
    if(level == "0") {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleView(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary),
                title = stringResource(id = R.string.unit, "1"),
                subtitle = stringResource(id = R.string.subtitle_unit1)
            )
            ButtonView(
                modifier = Modifier
                    .offset(x = dimensionResource(id = R.dimen.dm_2))
                    .size(dimensionResource(id = R.dimen.dm_7)),
                click = { level = "1.1" },
                text = stringResource(id = R.string.level, "1")
            )
            ButtonView(
                modifier = Modifier
                    .offset(x = dimensionResource(id = R.dimen.dm_5))
                    .size(dimensionResource(id = R.dimen.dm_7)),
                click = { level = "1.2" },
                enabled = myCourseCompleted >= 20,
                text = stringResource(id = R.string.level, "2")
            )
            ButtonView(
                modifier = Modifier
                    .offset(x = dimensionResource(id = R.dimen.dm_6))
                    .size(dimensionResource(id = R.dimen.dm_7)),
                click = { level = "1.3" },
                enabled = myCourseCompleted >= 40,
                text = stringResource(id = R.string.level, "3")
            )
            ButtonView(
                modifier = Modifier
                    .offset(x = dimensionResource(id = R.dimen.dm_2))
                    .size(dimensionResource(id = R.dimen.dm_7)),
                click = { level = "1.4" },
                enabled = myCourseCompleted >= 60,
                text = stringResource(id = R.string.level, "4")
            )
            Image(
                modifier = Modifier
                    .offset(
                        x = -dimensionResource(id = R.dimen.dm_6),
                        y = -dimensionResource(id = R.dimen.dm_10)
                    )
                    .size(dimensionResource(id = R.dimen.dm_7))
                    .clickable(enabled = myCourseCompleted >= 80) {
                        level = "1.5"
                    },
                painter = painterResource(id = R.drawable.steve_jobs),
                contentDescription = stringResource(id = R.string.foreground_login),
                contentScale = ContentScale.Fit
            )
        }
    }else{
        when(level){
            "1.1" -> LevelView(
                modifier = Modifier.fillMaxSize(),
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                myLife = myLife,
                progressLevel = progressLevel,
                isVisible = isVisible,
                onIsVisible = onIsVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = { id, isCorrect ->
                    if(id == 0) level = "0"
                    onCallback(id, isCorrect)
                }
            )
            "1.2" -> LevelView(
                modifier = Modifier.fillMaxSize(),
                exercise1 = Exercise1.data[1],
                exercise2 = Exercise2.data[1],
                exercise3 = Exercise3.data[1],
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                myLife = myLife,
                progressLevel = progressLevel,
                isVisible = isVisible,
                onIsVisible = onIsVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = { id, isCorrect ->
                    if(id == 0) level = "0"
                    onCallback(id, isCorrect)
                }
            )
            "1.3" -> LevelView(
                modifier = Modifier.fillMaxSize(),
                exercise1 = Exercise1.data[2],
                exercise2 = Exercise2.data[2],
                exercise3 = Exercise3.data[2],
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                myLife = myLife,
                progressLevel = progressLevel,
                isVisible = isVisible,
                onIsVisible = onIsVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = { id, isCorrect ->
                    if(id == 0) level = "0"
                    onCallback(id, isCorrect)
                }
            )
            "1.4" -> LevelView(
                modifier = Modifier.fillMaxSize(),
                exercise1 = Exercise1.data[3],
                exercise2 = Exercise2.data[3],
                exercise3 = Exercise3.data[3],
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                myLife = myLife,
                progressLevel = progressLevel,
                isVisible = isVisible,
                onIsVisible = onIsVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = { id, isCorrect ->
                    if(id == 0) level = "0"
                    onCallback(id, isCorrect)
                }
            )
            "1.5" -> StoryView(
                modifier = Modifier.fillMaxSize(),
                story = Stories.data[0],
                myLife = myLife,
                progressLevel = progressLevel,
                onProgressLevel = onProgressLevel,
                isVisible = isVisible,
                onIsTimerOff = onIsTimerOff,
                onCallback = { id, isCorrect ->
                    if(id == 0) level = "0"
                    onCallback(id, isCorrect)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewUnitsView(){
    EmprendimientoPrimariaTheme {
        UnitsView(
            modifier = Modifier.fillMaxSize(),
            myExperience = 0,
            myTimeSpent = 0,
            myCourseCompleted = 0,
            myLife = 3
        )
    }
}