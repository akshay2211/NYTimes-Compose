package io.ak1.nytimes.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.utility.timeAgo
import java.util.*

@Composable
fun PostElement(
    results: Results, viewModel: StoriesViewModel, clickCallback: (Results) -> Unit
) {
    val bookmarked = viewModel.isBookmarked(results.title ?: "").observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
            .clickable { clickCallback(results) }) {
        Card(elevation = 5.dp, modifier = Modifier.padding(16.dp, 8.dp)) {
            Image(
                painter = rememberCoilPainter(
                    request = results.url_large,
                    previewPlaceholder = android.R.color.darker_gray,
                    fadeIn = true
                ),
                contentDescription = results.title ?: "empty",
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        Row(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Column(modifier = Modifier.weight(1f, true)) {
                Text(
                    results.title ?: "empty",
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    results.published_date.timeAgo().toUpperCase(Locale.getDefault()),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                )
            }
            Image(
                painter = painterResource(if (bookmarked.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark),
                contentDescription = "bookmark icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                modifier = Modifier
                    .clickable {
                        if (bookmarked.value) {
                            viewModel.deleteBookmark(results, coroutineScope)
                        } else {
                            viewModel.addBookmark(results, coroutineScope)
                        }
                    }
                    .requiredWidth(56.dp)
                    .padding(12.dp),
            )


        }

    }
}