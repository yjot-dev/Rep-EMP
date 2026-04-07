package com.yjotdev.empprimaria.application.mvvm.view

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
import androidx.compose.runtime.getValue
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
import com.yjotdev.empprimaria.application.utils.ComponentPreview
import com.yjotdev.empprimaria.application.components.ButtonView
import com.yjotdev.empprimaria.application.components.TextView
import com.yjotdev.empprimaria.domain.utils.data.Stories
import com.yjotdev.empprimaria.domain.entity.StoryEntity
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.R

@Composable
fun StoryView(
    modifier: Modifier = Modifier,
    story: List<StoryEntity>,
    myLife: Int,
    progressLevel: Float = 0f,
    isVisible: Boolean = false,
    onProcess: (Int, Boolean) -> Unit = {_,_ ->},
    onCallback: () -> Unit = {}
){
    val scrollState = rememberScrollState()
    //Color de la barra de progreso segun su avance
    val colorLinearProgress = when(progressLevel){
        0.33f -> colorResource(id = R.color.red)
        0.66f -> colorResource(id = R.color.orange)
        else -> colorResource(id = R.color.green)
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
                onClick = onCallback,
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
                text = myLife.toString(),
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
                    val idSection = story.indexOf(section) + 1
                    onProcess(idSection, isCorrect)
                }
            }
            if (isVisible) {
                ButtonView(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dm_5))
                        .fillMaxWidth(0.85f),
                    click = { onCallback() },
                    text = stringResource(id = R.string.button_next)
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }
        }
    }
}

@Composable
private fun SectionView(section: StoryEntity, onResponse: (Boolean) -> Unit){
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
        text = stringResource(section.paragraph)
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
        text = stringResource(section.question)
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

@ComponentPreview
@Composable
private fun PreviewStoryView(){
    EmprendimientoPrimariaTheme {
        StoryView(
            modifier = Modifier.fillMaxSize(),
            story = Stories.data[0],
            myLife = 3,
            progressLevel = 0f
        )
    }
}