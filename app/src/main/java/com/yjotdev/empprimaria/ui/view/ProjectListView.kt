package com.yjotdev.empprimaria.ui.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.data.Projects
import com.yjotdev.empprimaria.ui.model.ProjectModel
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.view.utils.TextView
import com.yjotdev.empprimaria.ui.view.utils.TitleView

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
        val projectList by remember { mutableStateOf(Projects.list) }
        projectList.forEach { project ->
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
            isPreview = true
        )
    }
}