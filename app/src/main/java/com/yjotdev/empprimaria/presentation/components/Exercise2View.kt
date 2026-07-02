package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.domain.utils.Exercise2
import com.yjotdev.empprimaria.domain.model.Exercise2Model
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.utils.TestTags
import com.yjotdev.empprimaria.R

@Composable
fun Exercise2View(
    modifier: Modifier = Modifier,
    exercise2: Exercise2Model,
    isPreview: Boolean = false,
    onResponse: (Boolean) -> Unit
) {
    val tag = TestTags.EXERCISE_ANSWER_BUTTON.substring(0, 23)
    var isCorrect by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    val half = exercise2.answer.size / 2
    val firstHalf = exercise2.answer.take(half)
    val secondHalf = exercise2.answer.drop(half)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        GifImage(
            idImage = R.drawable.person_two,
            isPreview = isPreview
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        TextView(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface
                        .copy(alpha = 0.8f),
                    shape = ShapeDefaults.Large
                )
                .fillMaxWidth(0.85f),
            text = stringResource(exercise2.question)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        Column(
            verticalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.dm_2))
        ){
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                firstHalf.forEachIndexed { index, answer ->
                    ButtonView(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.dm_5))
                            .testTag(tag + index),
                        enabled = isVisible, //se desactiva al responder correcto
                        click = {
                            isCorrect = answer.second
                            isEnabled = true
                        },
                        text = stringResource(answer.first)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                secondHalf.forEachIndexed { index, answer ->
                    ButtonView(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.dm_5))
                            .testTag(tag + (index + half)),
                        enabled = isVisible, //se desactiva al responder correcto
                        click = {
                            isCorrect = answer.second
                            isEnabled = true
                        },
                        text = stringResource(answer.first)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        if(isVisible) {
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f)
                    .testTag(TestTags.EXERCISE_VERIFY_BUTTON),
                enabled = isEnabled, //se desactiva al responder incorrecto
                click = {
                    onResponse(isCorrect)
                    if (isCorrect) isVisible = false
                    else isEnabled = false
                },
                text = stringResource(id = R.string.button_verify_exercise)
            )
        }
    }
}

@ComponentPreview
@Composable
private fun PreviewExercise1View() {
    EmprendimientoPrimariaTheme {
        Exercise2View(
            modifier = Modifier.fillMaxSize(),
            exercise2 = Exercise2.data[0],
            isPreview = true,
            onResponse = {}
        )
    }
}