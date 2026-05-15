package com.yjotdev.empprimaria.presentation.mvvm.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.domain.model.ProjectModel
import com.yjotdev.empprimaria.domain.utils.Projects
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.components.TextView
import com.yjotdev.empprimaria.presentation.components.TitleView
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview

@Composable
fun ProjectListView(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Projects.list.forEach { project ->
            ProjectView(
                project = project,
                isPreview = isPreview
            )
        }
    }
}

@Composable
private fun ProjectView(project: ProjectModel, isPreview: Boolean){
    TitleView(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dm_5))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        title = stringResource(project.title)
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
    if(isPreview)
        Image(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dm_8))
                .fillMaxWidth(0.85f),
            painter = painterResource(id = R.drawable.login_icon),
            contentDescription = stringResource(id = R.string.foreground_login),
            contentScale = ContentScale.FillBounds
        )
    else
        AsyncImage(
            modifier = Modifier.fillMaxWidth(0.85f),
            model = project.imagePath,
            contentDescription = stringResource(id = R.string.foreground_login),
            contentScale = ContentScale.Fit
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
        text = stringResource(project.description)
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
}

@ComponentPreview
@Composable
private fun PreviewProjectListView(){
    EmprendimientoPrimariaTheme {
        ProjectListView(
            modifier = Modifier.fillMaxSize(),
            isPreview = true
        )
    }
}