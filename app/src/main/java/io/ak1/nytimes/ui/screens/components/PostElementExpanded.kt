package io.ak1.nytimes.ui.screens.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.utility.timeAgo

@Composable
fun PostElementExpanded(results: Results, clickCallback: (Results) -> Unit) {
    val context = LocalContext.current
    Column(Modifier.padding(16.dp)) {
        Card(elevation = 5.dp) {
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
        Spacer(Modifier.height(16.dp))


        Text(
            text = results.title ?: "empty",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = results.abstract_text ?: "empty",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = results.byline?.trim() ?: "",
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = results.published_date.timeAgo(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
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
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                Log.e("external", "url ${results.url}")
                Intent(Intent.ACTION_VIEW, Uri.parse(results.url ?: "")).let {
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_external_link),
                contentDescription = "hie",
                modifier = Modifier.padding(12.dp)
            )
            Text(
                text = stringResource(id = R.string.open_in_browser),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}