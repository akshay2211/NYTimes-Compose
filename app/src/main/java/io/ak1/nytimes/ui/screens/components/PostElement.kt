package io.ak1.nytimes.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.screens.home.StoriesViewModel

@Composable
fun PostElement(
    results: Results, viewModel: StoriesViewModel, clickCallback: (Results) -> Unit
) {
    val bookmarked = viewModel.checkBookMark(results.title ?: "").observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { clickCallback(results) }) {
        Card(elevation = 5.dp, modifier = Modifier.padding(16.dp, 16.dp)) {
            Image(
                painter = rememberCoilPainter(
                    request = results.url_large,
                    previewPlaceholder = R.drawable.gradient_bottom,
                    fadeIn = true
                ),
                contentDescription = results.title ?: "empty",
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Row {
            Text(
                text = results.title ?: "empty",
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 18.dp)
                    .width(250.dp)
            )
            Image(
                painter = painterResource(if (bookmarked.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark),
                contentDescription = "hie",
                //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .requiredWidth(72.dp)
                    .clickable {


                        if (bookmarked.value) {
                            viewModel.deleteBookmark(results, coroutineScope)
                        } else {
                            viewModel.addBookmark(results, coroutineScope)
                        }
                        //navController.navigate(MainDestinations.BOOKMARK_ROUTE)
                    }
                    .padding(12.dp)
            )

        }

    }
}