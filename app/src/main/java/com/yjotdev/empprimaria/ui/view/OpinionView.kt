package com.yjotdev.empprimaria.ui.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.ButtonView
import com.yjotdev.empprimaria.ui.view.utils.TextFieldView
import com.yjotdev.empprimaria.ui.view.utils.TitleView
import com.yjotdev.empprimaria.ui.viewmodel.ProgressViewModel

@Composable
fun OpinionView(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    onSendOpinion: (String) -> Unit
){
    val state by progressVm.uiState.collectAsState()
    var commentary by remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TitleView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            title = stringResource(id = R.string.my_progress)
        )
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
        TitleView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            title = stringResource(id = R.string.some_opinion)
        )
        TextFieldView(
            value = commentary,
            onValueChange = { commentary = it },
            labelId = R.string.text_field_commentary,
            infoId = R.string.valid_commentary,
            maxLines = 6,
            modifier = Modifier
                .verticalScroll(ScrollState(0))
                .fillMaxWidth(0.85f)
        )
        ButtonView(
            click = { onSendOpinion(commentary) },
            text = stringResource(id = R.string.button_send_opinion),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_7)))
    }
}

@Composable
fun ProgressChart(
    modifier: Modifier = Modifier,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Column{
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_experience),
                textResult = pluralStringResource(
                    id = R.plurals.points,
                    count = myExperience, myExperience
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_time_spent),
                textResult = pluralStringResource(
                    id = R.plurals.minutes,
                    count = myTimeSpent, myTimeSpent
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_course_completed),
                textResult = stringResource(id = R.string.my_percent, myCourseCompleted)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_3)))
        }
    }
}

@Composable
private fun ProgressInfo(
    modifier: Modifier = Modifier,
    textInfo: String,
    textResult: String
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = textInfo,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = textResult,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewTrackingAndSupportView(){
    EmprendimientoPrimariaTheme {
        OpinionView(
            modifier = Modifier.fillMaxSize(),
            progressVm = ProgressViewModel(),
            onSendOpinion = {}
        )
    }
}