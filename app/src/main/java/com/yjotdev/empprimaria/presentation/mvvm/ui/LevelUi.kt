package com.yjotdev.empprimaria.presentation.mvvm.ui

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
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.domain.model.Exercise1Model
import com.yjotdev.empprimaria.domain.model.Exercise2Model
import com.yjotdev.empprimaria.domain.model.Exercise3Model
import com.yjotdev.empprimaria.domain.utils.Exercise1
import com.yjotdev.empprimaria.domain.utils.Exercise2
import com.yjotdev.empprimaria.domain.utils.Exercise3
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.AnimationView
import com.yjotdev.empprimaria.presentation.components.Exercise1View
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.Exercise2View
import com.yjotdev.empprimaria.presentation.components.Exercise3View
import com.yjotdev.empprimaria.presentation.utils.TestTags
import com.yjotdev.empprimaria.R

@Composable
fun LevelView(
    modifier: Modifier = Modifier,
    exercise1: Exercise1Model = Exercise1.data[0],
    exercise2: Exercise2Model = Exercise2.data[0],
    exercise3: Exercise3Model = Exercise3.data[0],
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    myLife: Int,
    isBtnNextDisplayed: Boolean = false,
    onIsBtnNextDisplayed: (Boolean) -> Unit = {},
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
        when {
            nextExercise == 1 -> {
                Exercise1View(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    exercise1 = exercise1
                ) { isCorrect ->
                    onProcess(1, isCorrect)
                }
            }
            nextExercise == 2 -> {
                Exercise2View(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    exercise2 = exercise2
                ) { isCorrect ->
                    onProcess(2, isCorrect)
                }
            }
            nextExercise == 3 -> {
                Exercise3View(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    exercise3 = exercise3
                ) { isCorrect ->
                    onProcess(3, isCorrect)
                }
            }
            nextExercise == scoreId -> {
                UserProgress(
                    myExperience = myExperience,
                    myTimeSpent = myTimeSpent,
                    myCourseCompleted = myCourseCompleted,
                    modifier = Modifier.fillMaxWidth(0.85f)
                        .testTag(TestTags.OPINION_PROGRESS_CARD)
                )
            }
            myLife == 0 -> {
                AnimationView(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    title = stringResource(id = R.string.game_over)
                )
            }
        }
        if (isBtnNextDisplayed) {
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f)
                    .testTag(TestTags.LEVEL_NEXT_BUTTON),
                click = {
                    if (myLife == 0 || nextExercise == scoreId) {
                        onCallback()
                    } else {
                        val show = when(nextExercise) {
                            1, 2 -> { false }
                            else -> { true }
                        }
                        nextExercise += 1
                        onIsBtnNextDisplayed(show)
                    }
                },
                text = stringResource(id = R.string.button_next)
            )
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
            isBtnNextDisplayed = true
        )
    }
}