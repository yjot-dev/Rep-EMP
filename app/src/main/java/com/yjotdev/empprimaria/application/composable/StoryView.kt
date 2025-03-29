package com.yjotdev.empprimaria.ui.view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.yjotdev.empprimaria.data.Stories
import com.yjotdev.empprimaria.ui.model.StoryModel
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.viewmodel.ProgressViewModel
import kotlinx.coroutines.delay

@Composable
fun StoryView(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    story: List<StoryModel>,
    numLevel: Int,
    totalLevels: Int,
    onCallback: () -> Unit
){
    val progressUnit = (numLevel * 100)/totalLevels //Progreso de la unidad
    var progressLevel by remember { mutableFloatStateOf(0f) }
    var isVisible by remember { mutableStateOf(false) }
    var isTimerOff by remember { mutableStateOf(false) }
    val state by progressVm.uiState.collectAsState()
    val scrollState = rememberScrollState()
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
            if (progressLevel == 1f) {
                progressVm.setCourseCompleted(progressUnit)
                isTimerOff = true
                isVisible = true
            }
        }while(!isTimerOff)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            story.forEach { section ->
                SectionView(section = section) { isCorrect ->
                    if (isCorrect) {
                        val number = story.indexOf(section)
                        progressLevel = when (number) {
                            0 -> 0.33f
                            1 -> 0.66f
                            else -> 1f
                        }
                        progressVm.setExperience(state.experience + 20)
                    } else {
                        progressVm.setLife(state.life - 1)
                    }
                }
            }
            if (isVisible) {
                ButtonView(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dm_5))
                        .fillMaxWidth(0.85f),
                    click = onCallback,
                    text = stringResource(id = R.string.button_next)
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }
        }
    }
}

@Composable
private fun SectionView(section: StoryModel, onResponse: (Boolean) -> Unit){
    var isCorrect by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    val half = section.answer.size / 2
    val firstHalf = section.answer.take(half)
    val secondHalf = section.answer.drop(half)
    TextView(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface
                    .copy(alpha = 0.8f),
                shape = ShapeDefaults.Large
            )
            .fillMaxWidth(0.85f),
        text = section.paragraph
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
    TextView(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface
                    .copy(alpha = 0.8f),
                shape = ShapeDefaults.Large
            )
            .fillMaxWidth(0.85f),
        text = section.question
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
    Column(
        verticalArrangement = Arrangement
            .spacedBy(dimensionResource(id = R.dimen.dm_2))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            firstHalf.forEach { answer ->
                ButtonView(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dm_5)),
                    click = {
                        isCorrect = answer.second
                        isEnabled = true
                    },
                    text = answer.first
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            secondHalf.forEach { answer ->
                ButtonView(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dm_5)),
                    click = {
                        isCorrect = answer.second
                        isEnabled = true
                    },
                    text = answer.first
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
    if(isVisible) {
        ButtonView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
            enabled = isEnabled,
            click = {
                onResponse(isCorrect)
                if (isCorrect) isVisible = false
                else isEnabled = false
            },
            text = stringResource(id = R.string.button_verify_exercise)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewStoryView(){
    EmprendimientoPrimariaTheme {
        StoryView(
            modifier = Modifier.fillMaxSize(),
            progressVm = ProgressViewModel(),
            story = Stories.data[0],
            numLevel = 1,
            totalLevels = 5,
            onCallback = {}
        )
    }
}