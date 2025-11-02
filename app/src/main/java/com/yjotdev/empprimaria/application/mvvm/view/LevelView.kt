package com.yjotdev.empprimaria.application.mvvm.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.domain.entity.Exercise1Entity
import com.yjotdev.empprimaria.domain.entity.Exercise2Entity
import com.yjotdev.empprimaria.domain.entity.Exercise3Entity
import com.yjotdev.empprimaria.domain.utils.data.Exercise1
import com.yjotdev.empprimaria.domain.utils.data.Exercise2
import com.yjotdev.empprimaria.domain.utils.data.Exercise3
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.application.components.AnimationView
import com.yjotdev.empprimaria.application.components.Exercise1View
import com.yjotdev.empprimaria.application.components.ButtonView
import com.yjotdev.empprimaria.application.components.Exercise2View
import com.yjotdev.empprimaria.application.components.Exercise3View
import com.yjotdev.empprimaria.application.utils.ComponentPreview

@Composable
fun LevelView(
    modifier: Modifier = Modifier,
    exercise1: Exercise1Entity = Exercise1.data[0],
    exercise2: Exercise2Entity = Exercise2.data[0],
    exercise3: Exercise3Entity = Exercise3.data[0],
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    myLife: Int,
    isVisible: Boolean = false,
    onIsVisible: (Boolean) -> Unit = {},
    onIsTimerOff: (Boolean) -> Unit = {},
    onProcess: (Int, Boolean) -> Unit = {_,_ ->},
    onCallback: () -> Unit = {}
){
    val scoreId = 4 //Id de ventana del puntaje
    var nextExercise by remember { mutableIntStateOf(1) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(scoreId == nextExercise){
            onIsTimerOff(true)
            UserProgress(
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f),
                click = onCallback,
                text = stringResource(id = R.string.button_next)
            )
        }else if(myLife == 0){
            onIsTimerOff(true)
            AnimationView(
                modifier = Modifier.fillMaxWidth(0.85f),
                title = stringResource(id = R.string.game_over)
            )
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f),
                click = onCallback,
                text = stringResource(id = R.string.button_next)
            )
        }else {
            when (nextExercise) {
                1 -> {
                    Exercise1View(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        exercise1 = exercise1
                    ) { isCorrect ->
                        onProcess(1, isCorrect)
                    }
                }
                2 -> {
                    Exercise2View(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        exercise2 = exercise2
                    ) { isCorrect ->
                        onProcess(2, isCorrect)
                    }
                }
                3 -> {
                    Exercise3View(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        exercise3 = exercise3
                    ) { isCorrect ->
                        onProcess(3, isCorrect)
                    }
                }
            }
            if (isVisible) {
                ButtonView(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dm_5))
                        .fillMaxWidth(0.85f),
                    click = {
                        nextExercise += 1
                        onIsVisible(false)
                    },
                    text = stringResource(id = R.string.button_next)
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun PreviewLevelView(){
    EmprendimientoPrimariaTheme {
        LevelView(
            modifier = Modifier.fillMaxSize(),
            myExperience = 0,
            myTimeSpent = 0,
            myCourseCompleted = 0,
            myLife = 3,
            isVisible = true
        )
    }
}