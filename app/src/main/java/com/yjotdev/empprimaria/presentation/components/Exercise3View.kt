package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.domain.utils.Exercise3
import com.yjotdev.empprimaria.domain.model.Exercise3Model
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.R

@Composable
fun Exercise3View(
    modifier: Modifier = Modifier,
    exercise3: Exercise3Model,
    onResponse: (Boolean) -> Unit
) {
    var isCorrect by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    var responseText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val answer = stringResource(exercise3.answer)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextView(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface
                        .copy(alpha = 0.8f),
                    shape = ShapeDefaults.Large
                )
                .fillMaxWidth(0.85f),
            text = stringResource(exercise3.question)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        TextFieldView(
            enabled = isVisible, //se desactiva al responder correcto
            value = responseText,
            onValueChange = { text ->
                responseText = text
                isCorrect = text.equals(answer, ignoreCase = true)
                isEnabled = true
            },
            labelId = R.string.text_field_response,
            infoId = R.string.valid_response,
            onIsError = { isError = it }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        if(isVisible) {
            ButtonView(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dm_5))
                    .fillMaxWidth(0.85f),
                enabled = isEnabled && !isError, //se desactiva al responder incorrecto
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
        Exercise3View(
            modifier = Modifier.fillMaxSize(),
            exercise3 = Exercise3.data[0],
            onResponse = {}
        )
    }
}