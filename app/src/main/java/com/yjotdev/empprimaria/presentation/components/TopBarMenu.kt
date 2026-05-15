package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview

@Composable
fun TopBarMenu(
    modifier: Modifier = Modifier,
    onUserInfo: () -> Unit = {},
    onUnits: () -> Unit = {},
    onProjects: () -> Unit = {},
    onOpinion: () -> Unit = {}
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onUserInfo){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.user_info),
                contentDescription = stringResource(R.string.button_user_info),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = onUnits) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.units),
                contentDescription = stringResource(R.string.button_units),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = onProjects) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.projects),
                contentDescription = stringResource(R.string.button_projects),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = onOpinion) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.opinion),
                contentDescription = stringResource(R.string.button_opinion),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@ComponentPreview
@Composable
private fun PreviewTopBarMenu(){
    EmprendimientoPrimariaTheme {
        TopBarMenu(modifier = Modifier.fillMaxWidth())
    }
}