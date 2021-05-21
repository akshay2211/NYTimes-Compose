package io.ak1.nytimes.ui.screens.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.components.PostElement

@Composable
fun PostScreenComposable(
    postId: Int,
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    var story = liveViewModel.getStory(postId).observeAsState(Results())
    PostElement(results = story.value) {

    }
}