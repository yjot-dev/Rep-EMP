package com.yjotdev.empprimaria.presentation.mvvm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.ButtonView
import com.yjotdev.empprimaria.presentation.components.TitleView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview

@Composable
fun UnitsView(
    modifier: Modifier = Modifier,
    myCourseCompleted: Int,
    onNavigationToLevel: (String) -> Unit = {}
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleView(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            title = stringResource(id = R.string.unit, "1"),
            subtitle = stringResource(id = R.string.subtitle_unit1)
        )
        ButtonView(
            modifier = Modifier
                .offset(x = dimensionResource(id = R.dimen.dm_2))
                .size(dimensionResource(id = R.dimen.dm_7)),
            click = { onNavigationToLevel("1.1") },
            text = stringResource(id = R.string.level, "1")
        )
        ButtonView(
            modifier = Modifier
                .offset(x = dimensionResource(id = R.dimen.dm_5))
                .size(dimensionResource(id = R.dimen.dm_7)),
            click = { onNavigationToLevel("1.2") },
            enabled = myCourseCompleted >= 20,
            text = stringResource(id = R.string.level, "2")
        )
        ButtonView(
            modifier = Modifier
                .offset(x = dimensionResource(id = R.dimen.dm_6))
                .size(dimensionResource(id = R.dimen.dm_7)),
            click = { onNavigationToLevel("1.3") },
            enabled = myCourseCompleted >= 40,
            text = stringResource(id = R.string.level, "3")
        )
        ButtonView(
            modifier = Modifier
                .offset(x = dimensionResource(id = R.dimen.dm_2))
                .size(dimensionResource(id = R.dimen.dm_7)),
            click = { onNavigationToLevel("1.4") },
            enabled = myCourseCompleted >= 60,
            text = stringResource(id = R.string.level, "4")
        )
        IconButton(
            onClick = { onNavigationToLevel("1.5") },
            enabled = myCourseCompleted >= 80,
            modifier = Modifier.offset(
                    x = -dimensionResource(id = R.dimen.dm_6),
                    y = -dimensionResource(id = R.dimen.dm_10)
                ).size(dimensionResource(id = R.dimen.dm_7))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.steve_jobs),
                contentDescription = stringResource(id = R.string.foreground_login),
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(id = R.dimen.dm_6))
            )
        }
    }
}

@ComponentPreview
@Composable
private fun PreviewUnitsView(){
    EmprendimientoPrimariaTheme {
        UnitsView(
            modifier = Modifier.fillMaxSize(),
            myCourseCompleted = 0
        )
    }
}