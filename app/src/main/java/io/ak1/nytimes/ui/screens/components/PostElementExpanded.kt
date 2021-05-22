package io.ak1.nytimes.ui.screens.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.utility.timeAgo

@Composable
fun PostElementExpanded(results: Results, clickCallback: (Results) -> Unit) {
    Column {
        Card(elevation = 5.dp, modifier = Modifier.padding(16.dp, 0.dp)) {
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
        Text(
            text = results.title ?: "empty",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 12.dp)
                .fillMaxWidth()
        )
        Text(
            text = results.abstract_text ?: "empty",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = results.byline?.trim() ?: "",
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 8.dp)
                .wrapContentWidth(Alignment.End)
        )
        Text(
            text = results.published_date.timeAgo(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 24.dp)
                .wrapContentWidth(Alignment.End)
        )

        val tags = results.des_facet.trim().split(",")
        Log.e("all data ", "-> ${results.des_facet.trim()} ${tags.size}")
        if (false) {
            LazyRow(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
            ) {
                items(tags) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 0.dp, 0.dp)
                            .wrapContentSize()
                            .background(
                                MaterialTheme.colors.secondary,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(18.dp, 10.dp)
                    )

                }


            }
        }
    }
    // TODO: 21/05/21 remove settings icon from here
    // TODO: 21/05/21 add external link and share icons with there functionalities
}