package io.ak1.nytimes.ui.screens.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    var story = liveViewModel.getStory(postId).observeAsState(Results())
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
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "hie",
                    //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .clickable {
                            //navController.navigate(MainDestinations.BOOKMARK_ROUTE)
                        }
                        .padding(12.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "hie",
                    modifier = Modifier
                        .clickable {
                            //   navController.navigate(MainDestinations.SETTINGS_ROUTE)
                        }
                        .padding(12.dp)
                )
            }
        }
        PostElementExpanded(results = story.value) {

        }
    }

}