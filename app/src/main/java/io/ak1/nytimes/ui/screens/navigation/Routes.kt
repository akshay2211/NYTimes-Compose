package io.ak1.nytimes.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.ak1.nytimes.ui.home.StoriesViewModel

/**
 * Destinations used.
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val POST_ROUTE = "post"
    const val BOOKMARK_ROUTE = "bookmarks"
    const val SETTINGS_ROUTE = "settings"
    const val POST_ID_KEY = "postId"
}


// TODO: 21/05/21 remove from here add to designated places
@Composable
fun SettingsScreenComposable(
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {

}
