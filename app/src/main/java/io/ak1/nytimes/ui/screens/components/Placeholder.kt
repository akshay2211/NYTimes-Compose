package io.ak1.nytimes.ui.screens.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.shimmer.ShimmerFrameLayout
import io.ak1.nytimes.R

@Composable
fun PlaceHolder(
    @DrawableRes imageId: Int? = null,
    @StringRes titleId: Int? = null,
    @StringRes descriptionId: Int? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        imageId?.let {
            Image(
                painter = painterResource(it),
                contentDescription = stringResource(R.string.image_desc),
                modifier = Modifier
                    .padding(12.dp)
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        titleId?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(0.dp, 9.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        descriptionId?.let {
            Text(
                text = stringResource(id = it),
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

@Composable
fun ShimmerPlaceholder() {
    Shimmer {
        Column {
            Card(
                backgroundColor = colorResource(id = android.R.color.darker_gray),
                elevation = 5.dp, modifier = Modifier
                    .padding(16.dp, 12.dp)
                    .requiredHeight(214.dp)
                    .fillMaxWidth()
            ) {

            }

            Card(
                backgroundColor = colorResource(id = android.R.color.darker_gray),
                elevation = 5.dp, modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .requiredHeight(40.dp)
                    .fillMaxWidth()
            ) {

            }

            Card(
                backgroundColor = colorResource(id = android.R.color.darker_gray),
                elevation = 5.dp, modifier = Modifier
                    .padding(16.dp, 12.dp)
                    .requiredHeight(40.dp)
                    .fillMaxWidth()
            ) {

            }
        }
    }
}