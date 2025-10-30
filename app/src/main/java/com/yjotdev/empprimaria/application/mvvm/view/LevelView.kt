package com.yjotdev.empprimaria.application.mvvm.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    progressLevel: Float = 0f,
    isVisible: Boolean = false,
    onIsVisible: (Boolean) -> Unit = {},
    onIsTimerOff: (Boolean) -> Unit = {},
    onCallback: (Int, Boolean?) -> Unit = {_,_ ->}
){
    val scoreId = 4 //Id de ventana del puntaje
    var nextExercise by remember { mutableIntStateOf(1) }
    //Color de la barra de progreso segun su avance
    val colorLinearProgress = when(progressLevel){
        0.33f -> colorResource(id = R.color.red)
        0.66f -> colorResource(id = R.color.orange)
        else -> colorResource(id = R.color.green)
    }
    onCallback(-2, null)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(scoreId == nextExercise){
            onCallback(-1, null)
            onIsTimerOff(true)
            Spacer(modifier = Modifier.weight(0.1f))
            ProgressChart(
                myExperience = myExperience,
                myTimeSpent = myTimeSpent,
                myCourseCompleted = myCourseCompleted,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface
                            .copy(alpha = 0.8f),
                        shape = ShapeDefaults.Large
                    )
                    .fillMaxWidth(0.85f)
            )
            Spacer(modifier = Modifier.weight(0.8f))
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f),
                click = { onCallback(0, null) },
                text = stringResource(id = R.string.button_next)
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }else if(myLife == 0){
            Spacer(modifier = Modifier.weight(0.1f))
            AnimationView(
                modifier = Modifier.fillMaxWidth(0.85f),
                title = stringResource(id = R.string.game_over)
            )
            Spacer(modifier = Modifier.weight(0.8f))
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f),
                click = { onCallback(0, null) },
                text = stringResource(id = R.string.button_next)
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }else {
            Row(
                modifier = Modifier.fillMaxWidth(0.95f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dm_5))
                        .clickable {
                            onIsTimerOff(true)
                            onCallback(0, null)
                        }
                )
                LinearProgressIndicator(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)),
                    progress = { progressLevel },
                    color = colorLinearProgress,
                    trackColor = colorResource(id = R.color.white),
                )
                Text(
                    text = myLife.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.red)
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.user_live),
                    contentDescription = null,
                    tint = colorResource(id = R.color.red),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dm_4))
                )
            }
            when (nextExercise) {
                1 -> {
                    Exercise1View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise1 = exercise1
                    ) { isCorrect ->
                        onCallback(1, isCorrect)
                    }
                }
                2 -> {
                    Exercise2View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise2 = exercise2
                    ) { isCorrect ->
                        onCallback(2, isCorrect)
                    }
                }
                3 -> {
                    Exercise3View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise3 = exercise3
                    ) { isCorrect ->
                        onCallback(3, isCorrect)
                    }
                }
            }
            if (isVisible) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
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

@Preview(
    showBackground = false,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewLevelView(){
    EmprendimientoPrimariaTheme {
        LevelView(
            modifier = Modifier.fillMaxSize(),
            myExperience = 0,
            myTimeSpent = 0,
            myCourseCompleted = 0,
            myLife = 3
        )
    }
}