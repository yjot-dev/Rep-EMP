package com.yjotdev.empprimaria.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.yjotdev.empprimaria.data.Exercise1
import com.yjotdev.empprimaria.data.Exercise2
import com.yjotdev.empprimaria.data.Exercise3
import com.yjotdev.empprimaria.ui.model.Exercise1Model
import com.yjotdev.empprimaria.ui.model.Exercise2Model
import com.yjotdev.empprimaria.ui.model.Exercise3Model
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.AnimationView
import com.yjotdev.empprimaria.ui.view.utils.Exercise1View
import com.yjotdev.empprimaria.ui.view.utils.ButtonView
import com.yjotdev.empprimaria.ui.view.utils.Exercise2View
import com.yjotdev.empprimaria.ui.view.utils.Exercise3View
import com.yjotdev.empprimaria.ui.viewmodel.ProgressViewModel
import kotlinx.coroutines.delay

@Composable
fun LevelView(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    exercise1: Exercise1Model,
    exercise2: Exercise2Model,
    exercise3: Exercise3Model,
    numLevel: Int,
    totalLevels: Int,
    onCallback: () -> Unit
){
    val scoreId = 4 //Id de ventana del puntaje
    val progressUnit = (numLevel * 100)/totalLevels //Progreso de la unidad
    var progressLevel by remember { mutableFloatStateOf(0f) }
    var nextExercise by remember { mutableIntStateOf(1) }
    var isVisible by remember { mutableStateOf(false) }
    var isTimerOff by remember { mutableStateOf(false) }
    val state by progressVm.uiState.collectAsState()
    //Color de la barra de progreso segun su avance
    val colorLinearProgress = when(progressLevel){
        0.33f -> colorResource(id = R.color.red)
        0.66f -> colorResource(id = R.color.orange)
        else -> colorResource(id = R.color.green)
    }
    LaunchedEffect(key1 = state.timeSpent) {
        //Temporizador activo caso contrario se detiene
        do{
            delay(1000 * 60)
            progressVm.setTimeSpent(state.timeSpent + 1)
        }while(!isTimerOff)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(scoreId == nextExercise){
            progressVm.setCourseCompleted(progressUnit)
            isTimerOff = true
            Spacer(modifier = Modifier.weight(0.1f))
            ProgressChart(
                myExperience = state.experience,
                myTimeSpent = state.timeSpent,
                myCourseCompleted = state.courseCompleted,
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
                click = onCallback,
                text = stringResource(id = R.string.button_next)
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }else if(state.life == 0){
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
                click = onCallback,
                text = stringResource(id = R.string.button_next)
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }else {
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(
                    onClick = {
                        isTimerOff = true
                        onCallback()
                    },
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dm_5))
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dm_5))
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)),
                    progress = { progressLevel },
                    color = colorLinearProgress,
                    trackColor = colorResource(id = R.color.white)
                )
                Text(
                    text = state.life.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.red)
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.user_live),
                    contentDescription = null,
                    tint = colorResource(id = R.color.red)
                )
            }
            when (nextExercise) {
                1 -> {
                    Exercise1View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise1 = exercise1
                    ) { isCorrect ->
                        if(isCorrect) {
                            progressLevel = 0.33f
                            progressVm.setExperience(state.experience + 20)
                            isVisible = true
                        }else{
                            progressVm.setLife(state.life - 1)
                        }
                    }
                }
                2 -> {
                    Exercise2View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise2 = exercise2
                    ) { isCorrect ->
                        if(isCorrect) {
                            progressLevel = 0.66f
                            progressVm.setExperience(state.experience + 20)
                            isVisible = true
                        }else{
                            progressVm.setLife(state.life - 1)
                        }
                    }
                }
                3 -> {
                    Exercise3View(
                        modifier = if(isVisible) Modifier.weight(0.6f)
                                   else Modifier.weight(0.7f),
                        exercise3 = exercise3
                    ) { isCorrect ->
                        if(isCorrect) {
                            progressLevel = 1f
                            progressVm.setExperience(state.experience + 20)
                            isVisible = true
                        }else{
                            progressVm.setLife(state.life - 1)
                        }
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
                        isVisible = false
                    },
                    text = stringResource(id = R.string.button_next)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewLevelView(){
    EmprendimientoPrimariaTheme {
        LevelView(
            modifier = Modifier.fillMaxSize(),
            progressVm = ProgressViewModel(),
            exercise1 = Exercise1.data[0],
            exercise2 = Exercise2.data[0],
            exercise3 = Exercise3.data[0],
            numLevel = 1,
            totalLevels = 5,
            onCallback = {}
        )
    }
}