package io.ak1.nytimes.ui.screens.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.shimmer.ShimmerFrameLayout

@Composable
fun PlaceHolder(
    @DrawableRes imageId: Int = 0,
    @StringRes titleId: Int = 0,
    @StringRes descriptionId: Int = 0
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        if (imageId != 0) {
            Image(
                painter = painterResource(imageId),
                contentDescription = "hie",
                modifier = Modifier
                    .padding(12.dp)
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        if (titleId != 0) {
            Text(
                text = stringResource(id = titleId),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(0.dp, 9.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        if (descriptionId != 0) {
            Text(
                text = stringResource(id = descriptionId),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Shimmer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val shimmer = remember {
        ShimmerFrameLayout(context).apply {
            addView(ComposeView(context).apply {
                setContent(content)
            })
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { shimmer }
    ) { it.startShimmer() }
}