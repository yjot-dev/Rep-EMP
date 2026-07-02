package com.yjotdev.empprimaria.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.platform.testTag
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.utils.ComponentPreview
import com.yjotdev.empprimaria.presentation.utils.TestTags

/**
 * Un Composable que muestra un indicador de progreso circular en el centro de la pantalla.
 * Ideal para superponer sobre otro contenido mientras se cargan datos.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.testTag(TestTags.LOADING_INDICATOR))
    }
}

@ComponentPreview
@Composable
fun PreviewLoadingScreen() {
    EmprendimientoPrimariaTheme {
        LoadingScreen(modifier = Modifier.size(dimensionResource(R.dimen.dm_8)))
    }
}