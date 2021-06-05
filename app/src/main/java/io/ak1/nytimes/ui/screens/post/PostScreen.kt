package io.ak1.nytimes.ui.screens.post

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.screens.components.CustomAlertDialog
import io.ak1.nytimes.ui.screens.components.CustomAppBar
import io.ak1.nytimes.ui.screens.components.PostElementExpanded
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.navigation.MainDestinations

@Composable
fun PostScreenComposable(
    from: String,
    postId: Int,
    viewModel: StoriesViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val story = viewModel.let {
        if (from == MainDestinations.HOME_ROUTE) {
            it.getStory(postId)
        } else {
            it.getBookmark(postId)
        }
    }.observeAsState(initial = Results())

    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize()) {
        CustomAppBar(navController = navController, viewModel = viewModel, story = story) {
            if (it) {
                setShowDialog(true)
            } else {
                viewModel.addBookmark(story.value, coroutineScope)
            }
        }
        PostElementExpanded(
            results = story,
            modifier = Modifier
                .width(600.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
    CustomAlertDialog(
        titleId = R.string.deletion_confirmation,
        showDialog = showDialog,
        setShowDialog = setShowDialog
    ) {
        navController.navigateUp()
        viewModel.deleteBookmark(story.value, coroutineScope)
        Toast.makeText(context, R.string.bookmark_removed, Toast.LENGTH_LONG).show()
    }

}