package com.yjotdev.empprimaria.ui.view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.data.Exercise2
import com.yjotdev.empprimaria.ui.model.Exercise2Model
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme

@Composable
fun Exercise2View(
    modifier: Modifier = Modifier,
    exercise2: Exercise2Model,
    isPreview: Boolean = false,
    onResponse: (Boolean) -> Unit
) {
    var isCorrect by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    val half = exercise2.answer.size / 2
    val firstHalf = exercise2.answer.take(half)
    val secondHalf = exercise2.answer.drop(half)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GifImage(
                idImage = R.drawable.person_two,
                isPreview = isPreview
            )
            TextView(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface
                            .copy(alpha = 0.8f),
                        shape = ShapeDefaults.Large
                    )
                    .fillMaxWidth(0.85f),
                text = exercise2.question
            )
        }
        Column(
            verticalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.dm_2))
        ){
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
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
            ){
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
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
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