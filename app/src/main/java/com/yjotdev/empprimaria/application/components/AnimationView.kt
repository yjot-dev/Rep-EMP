package com.yjotdev.empprimaria.application.components

import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.application.utils.ComponentPreview

@Composable
fun AnimationView(
    modifier: Modifier = Modifier,
    title: String,
    isPreview: Boolean = false
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = ShapeDefaults.ExtraLarge
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dm_3)),
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            GifImage(
                idImage = R.drawable.person_one,
                isPreview = isPreview
            )
            GifImage(
                idImage = R.drawable.person_two,
                isPreview = isPreview
            )
        }
    }
}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    @DrawableRes idImage: Int,
    isPreview: Boolean = false
){
    if(isPreview){
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.login_icon),
            contentDescription = stringResource(id = R.string.foreground_login),
            contentScale = ContentScale.Fit
        )
    }
    else {
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if(SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
                else add(GifDecoder.Factory())}
            .build()
        Image(
            modifier = modifier,
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context).data(idImage)
                    .apply{ size(Size.ORIGINAL) }.build(),
                imageLoader = imageLoader),
            contentDescription = stringResource(id = R.string.foreground_login),
            contentScale = ContentScale.Fit
        )
    }
}

@ComponentPreview
@Composable
private fun PreviewAnimationView(){
    EmprendimientoPrimariaTheme {
        AnimationView(
            modifier = Modifier.fillMaxSize(),
            title = "Game Over",
            isPreview = true
        )
    }
}