package com.yjotdev.empprimaria.ui.view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.yjotdev.empprimaria.data.Exercise3
import com.yjotdev.empprimaria.ui.model.Exercise3Model
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme

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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
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
            text = exercise3.question
        )
        TextFieldView(
            value = responseText,
            onValueChange = { text ->
                responseText = text
                isCorrect = text == exercise3.answer
                isEnabled = true
            },
            labelId = R.string.text_field_response,
            infoId = R.string.valid_response
        )
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
        Exercise3View(
            modifier = Modifier.fillMaxSize(),
            exercise3 = Exercise3.data[0],
            onResponse = {}
        )
    }
}