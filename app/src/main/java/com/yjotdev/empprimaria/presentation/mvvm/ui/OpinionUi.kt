package com.yjotdev.empprimaria.presentation.mvvm.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TextFieldView
import com.yjotdev.empprimaria.presentation.components.TitleView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.Helper
import com.yjotdev.empprimaria.presentation.utils.TestTags
import com.yjotdev.empprimaria.R

@Composable
fun OpinionView(
    modifier: Modifier = Modifier,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int,
    onSendOpinion: (String) -> Unit
){
    var commentary by remember { mutableStateOf("") }
    val isValidMessage = Helper.isValidMessage(commentary)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TitleView(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .testTag(TestTags.OPINION_TITLE),
            title = stringResource(id = R.string.my_progress)
        )
        UserProgress(
            myExperience = myExperience,
            myTimeSpent = myTimeSpent,
            myCourseCompleted = myCourseCompleted,
            modifier = Modifier.fillMaxWidth(0.85f)
                .testTag(TestTags.OPINION_PROGRESS_CARD)
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
            validateCase = isValidMessage,
            modifier = Modifier
                .verticalScroll(ScrollState(0))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.OPINION_COMMENTARY_FIELD)
        )
        ButtonView(
            click = { onSendOpinion(commentary) },
            text = stringResource(id = R.string.button_send_opinion),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_5))
                .fillMaxWidth(0.85f)
                .testTag(TestTags.OPINION_SUBMIT_BUTTON),
            enabled = isValidMessage
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_7)))
    }
}

@Composable
fun UserProgress(
    modifier: Modifier = Modifier,
    myExperience: Int,
    myTimeSpent: Int,
    myCourseCompleted: Int
){
    Card(
        modifier = modifier,
        shape = ShapeDefaults.ExtraLarge
    ){
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dm_3))
                               .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_experience),
                textResult = pluralStringResource(
                    id = R.plurals.points,
                    count = myExperience, myExperience
                )
            )
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_time_spent),
                textResult = pluralStringResource(
                    id = R.plurals.minutes,
                    count = myTimeSpent, myTimeSpent
                )
            )
            ProgressInfo(
                modifier = Modifier.fillMaxWidth(0.9f),
                textInfo = stringResource(id = R.string.my_course_completed),
                textResult = stringResource(id = R.string.my_percent, myCourseCompleted)
            )
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
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = textInfo,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = textResult,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@ComponentPreview
@Composable
private fun PreviewTrackingAndSupportView(){
    EmprendimientoPrimariaTheme {
        OpinionView(
            modifier = Modifier.fillMaxSize(),
            myExperience = 0,
            myTimeSpent = 0,
            myCourseCompleted = 0,
            onSendOpinion = {}
        )
    }
}