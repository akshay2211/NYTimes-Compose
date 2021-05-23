package io.ak1.nytimes.ui.screens.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.components.PostElementExpanded

@Composable
fun PostScreenComposable(
    postId: Int,
    viewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    var story = viewModel.getStory(postId).observeAsState(Results())
    val bookmarked =
        viewModel.checkBookMark(story.value.title ?: "").observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()


    Column {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(if (bookmarked.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark),
                    contentDescription = "hie",
                    //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .clickable {
                            if (bookmarked.value) {
                                viewModel.deleteBookmark(story.value, coroutineScope)
                            } else {
                                viewModel.addBookmark(story.value, coroutineScope)
                            }
                        }
                        .padding(12.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "share link",
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(12.dp)
                )
            }
        }
        PostElementExpanded(results = story.value) {

        }
    }

}