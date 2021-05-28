package io.ak1.nytimes.ui.screens.components

import android.view.Window
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import io.ak1.nytimes.ui.screens.bookmark.BookmarksScreenComposable
import io.ak1.nytimes.ui.screens.home.HomeScreenComposable
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.navigation.MainDestinations
import io.ak1.nytimes.ui.screens.post.PostScreenComposable
import io.ak1.nytimes.ui.screens.settings.SettingsScreen
import io.ak1.nytimes.ui.theme.TheNewYorkTimesAppTheme


@Composable
fun RootComponent(viewModel: StoriesViewModel, window: Window) {

    val isDark = isSystemInDarkThemeCustom()
    TheNewYorkTimesAppTheme(isDark) {
        val listState = rememberLazyListState()
        window.StatusBarConfig(isDark)
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = MainDestinations.HOME_ROUTE
            ) {
                composable(MainDestinations.HOME_ROUTE) {
                    HomeScreenComposable(listState, viewModel, navController)
                }
                composable(
                    "${MainDestinations.POST_ROUTE}/{${MainDestinations.POST_ID_KEY}}/{${MainDestinations.POST_FROM_KEY}}",
                    arguments = listOf(navArgument(MainDestinations.POST_ID_KEY) {
                        type = NavType.IntType
                    }, navArgument(MainDestinations.POST_FROM_KEY) {
                        type = NavType.StringType
                    })
                ) {
                    var arg = requireNotNull(it.arguments)
                    val postId = arg.getInt(MainDestinations.POST_ID_KEY)
                    val postFrom =
                        arg.getString(MainDestinations.POST_FROM_KEY) ?: MainDestinations.HOME_ROUTE
                    PostScreenComposable(postFrom, postId, viewModel, navController)
                }
                composable(MainDestinations.SETTINGS_ROUTE) {
                    SettingsScreen(viewModel, navController)
                }
                composable(MainDestinations.BOOKMARK_ROUTE) {
                    BookmarksScreenComposable(viewModel, navController)
                }
            }
        }
    }
}

