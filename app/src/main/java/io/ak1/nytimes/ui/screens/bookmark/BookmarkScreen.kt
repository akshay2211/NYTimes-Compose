package io.ak1.nytimes.ui.screens.bookmark

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.home.StoriesViewModel

// TODO: 22/05/21 add smaller list with circular icons
@Composable
fun BookmarksScreenComposable(
    viewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    val resultList = viewModel.bookmarks.observeAsState(initial = listOf())
    Scaffold(
        topBar = {
            Row(modifier = Modifier.padding(4.dp)) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "hie",
                    //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .clickable {
                            navController.navigateUp()
                        }
                        .padding(12.dp)
                )
                Text(
                    text = stringResource(id = R.string.bookmarks_title),
                    style = MaterialTheme.typography.h6, modifier = Modifier.padding(0.dp, 9.dp)
                )
            }
        }
    ) {
        Column {

            LazyColumn {

                itemsIndexed(resultList.value) { pos, element ->
                    var bookmarked = rememberSaveable {
                        false
                    }
                    Log.e("check pos", "->  ${pos}")

                    Box {
                        Card(elevation = 5.dp, modifier = Modifier.padding(16.dp, 16.dp)) {
                            Image(
                                painter = rememberCoilPainter(
                                    request = element.url_large,
                                    previewPlaceholder = R.drawable.gradient_bottom,
                                    fadeIn = true
                                ),
                                contentDescription = element.title ?: "empty",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(300.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = element.title ?: "empty",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(16.dp, 16.dp, 16.dp, 12.dp)
                                .fillMaxWidth()
                                .align(
                                    Alignment.BottomStart
                                )
                        )

                    }
                }
            }
        }
    }
}
