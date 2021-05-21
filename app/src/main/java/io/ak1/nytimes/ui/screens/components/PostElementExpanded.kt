package io.ak1.nytimes.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results

@Composable
fun PostElementExpanded(results: Results, clickCallback: (Results) -> Unit) {
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
        Text(
            text = results.title ?: "empty",
            modifier = Modifier
                .padding(4.dp, 0.dp, 0.dp, 18.dp)
                .fillMaxWidth()
        )
    }
}