package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.navigation.ViewRoutes
import com.yjotdev.empprimaria.presentation.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    currentScreen: ViewRoutes,
    canNavigateBack: Boolean,
    progressLevel: Float,
    myLife: Int,
    navigateUp: () -> Unit,
    onUserInfo: () -> Unit,
    onUnits: () -> Unit,
    onProjects: () -> Unit,
    onOpinion: () -> Unit
){
    // No muestres encabezado para la pantalla de Login
    if (currentScreen == ViewRoutes.Login) {
        return
    }
    // Define las rutas que mostrarán el TopBarMenu
    val screensWithTopBarMenu = setOf(
        ViewRoutes.UserInfo,
        ViewRoutes.Units,
        ViewRoutes.Projects,
        ViewRoutes.Opinion
    )
    // Define las rutas que mostrarán el progreso del nivel
    val screensWithProgressLevel = setOf(
        ViewRoutes.Level,
        ViewRoutes.Story
    )
    // Renderiza la TopAppBar con el contenido correcto
    TopAppBar(
        title = {
            when (currentScreen) {
                in screensWithTopBarMenu -> {
                    TopBarMenu(
                        modifier = modifier,
                        onUserInfo = onUserInfo,
                        onUnits = onUnits,
                        onProjects = onProjects,
                        onOpinion = onOpinion
                    )
                }
                in screensWithProgressLevel -> {
                    TopBarProgress(
                        modifier = modifier,
                        progressLevel = progressLevel,
                        myLife = myLife
                    )
                }
                else -> {
                    Text(
                        modifier = modifier,
                        text = stringResource(id = currentScreen.idTitle),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dm_5))
                        .testTag(TestTags.TITLE_BAR_BACK_BUTTON)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = null,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dm_5))
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary
        )
    )
}
