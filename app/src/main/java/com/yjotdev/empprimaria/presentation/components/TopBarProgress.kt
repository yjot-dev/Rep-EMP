package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.TestTags

@Composable
fun TopBarProgress(
    modifier: Modifier = Modifier,
    progressLevel: Float,
    myLife: Int
) {
    //Color de la barra de progreso según su avance
    val colorLinearProgress = when(progressLevel){
        0.33f -> colorResource(id = R.color.red)
        0.66f -> colorResource(id = R.color.orange)
        else -> colorResource(id = R.color.green)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ){
        LinearProgressIndicator(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_3))
                .testTag(TestTags.TOP_BAR_PROGRESS_INDICATOR),
            progress = { progressLevel },
            color = colorLinearProgress,
            trackColor = colorResource(R.color.white)
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
}

@ComponentPreview
@Composable
private fun PreviewTopBarProgress(){
    EmprendimientoPrimariaTheme {
        TopBarProgress(
            modifier = Modifier.fillMaxWidth(),
            progressLevel = 0.33f,
            myLife = 3
        )
    }
}