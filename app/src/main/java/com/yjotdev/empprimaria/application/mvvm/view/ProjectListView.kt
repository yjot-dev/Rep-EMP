package com.yjotdev.empprimaria.application.mvvm.view

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.domain.entity.ProjectEntity
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.application.components.TextView
import com.yjotdev.empprimaria.application.components.TitleView
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel

@Composable
fun ProjectListView(
    modifier: Modifier = Modifier,
    progressVm: ProgressViewModel,
    isPreview: Boolean = false
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        progressVm.projectList.forEach { project ->
            ProjectView(
                project = project,
                isPreview = isPreview
            )
        }
    }
}

@Composable
private fun ProjectView(project: ProjectEntity, isPreview: Boolean){
    TitleView(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dm_5))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        title = project.title
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
        text = project.description
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dm_4)))
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewProjectListView(){
    EmprendimientoPrimariaTheme {
        ProjectListView(
            modifier = Modifier.fillMaxSize(),
            progressVm = viewModel(),
            isPreview = true
        )
    }
}