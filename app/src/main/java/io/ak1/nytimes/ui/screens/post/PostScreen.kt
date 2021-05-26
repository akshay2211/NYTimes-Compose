package io.ak1.nytimes.ui.screens.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.screens.components.CustomAppBar
import io.ak1.nytimes.ui.screens.components.PostElementExpanded
import io.ak1.nytimes.ui.screens.home.StoriesViewModel

@Composable
fun PostScreenComposable(
    postId: Int,
    viewModel: StoriesViewModel,
    navController: NavController
) {
    var story = viewModel.getStory(postId).observeAsState(Results())

    Column(Modifier.fillMaxSize()) {
        CustomAppBar(navController = navController, viewModel = viewModel, story = story.value)
        PostElementExpanded(results = story.value)
    }

}