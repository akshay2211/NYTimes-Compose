package io.ak1.nytimes.ui.screens.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.screens.components.DefaultAppBar
import io.ak1.nytimes.ui.screens.components.PlaceHolder
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.navigation.MainDestinations


@Composable
fun BookmarksScreenComposable(
    viewModel: StoriesViewModel,
    navController: NavController
) {
    val resultList = viewModel.bookmarks.observeAsState(initial = listOf())
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(titleId = R.string.bookmarks_title, navController = navController)
        }
    ) {

        if (resultList.value.isEmpty()) {
            PlaceHolder(
                R.drawable.ic_undraw_empty_xct9,
                R.string.no_bookmarks,
                R.string.add_bookmarks
            )

        } else {
            LazyColumn {
                itemsIndexed(resultList.value) { _, element ->

                    Row(modifier = Modifier
                        .clickable {
                            navController.navigate("${MainDestinations.POST_ROUTE}/${element.id}/${MainDestinations.BOOKMARK_ROUTE}")
                        }
                        .padding(16.dp, 0.dp)
                        .height(88.dp)) {
                        Image(
                            painter = rememberCoilPainter(
                                request = element.urlLarge,
                                previewPlaceholder = android.R.color.darker_gray,
                                fadeIn = true
                            ), contentDescription = element.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically)
                        )
                        Text(
                            text = element.title ?: "empty",
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth()

                        )

                    }
                    Spacer(Modifier.height(16.dp))

                }
            }
        }
    }
}
