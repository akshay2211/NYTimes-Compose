package io.ak1.nytimes.ui.screens.components

import android.util.Log
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.home.HomeScreenComposable
import io.ak1.nytimes.ui.screens.navigation.BookmarksScreenComposable
import io.ak1.nytimes.ui.screens.navigation.MainDestinations
import io.ak1.nytimes.ui.screens.navigation.SettingsScreenComposable
import io.ak1.nytimes.ui.screens.post.PostScreenComposable
import io.ak1.nytimes.ui.theme.TheNewYorkTimesAppTheme


@Composable
fun RootComponent(viewModel: StoriesViewModel, window: Window) {
    TheNewYorkTimesAppTheme {
        val listState = rememberLazyListState()
        window.setupStatusBar()
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
                    "${MainDestinations.POST_ROUTE}/{${MainDestinations.POST_ID_KEY}}",
                    arguments = listOf(navArgument(MainDestinations.POST_ID_KEY) {
                        type = NavType.IntType
                    })
                ) {
                    val arguments = requireNotNull(it.arguments)
                    val postId = arguments.getInt(MainDestinations.POST_ID_KEY)
                    Log.e("saved state", "->  ${listState.firstVisibleItemIndex}")
                    PostScreenComposable(postId, viewModel, navController) {

                    }
                }
                composable(MainDestinations.SETTINGS_ROUTE) {
                    SettingsScreenComposable(viewModel, navController) {
                    }
                }
                composable(MainDestinations.BOOKMARK_ROUTE) {
                    BookmarksScreenComposable(viewModel, navController) {
                    }
                }
            }
        }
    }
}

@Composable
fun Window.setupStatusBar() {
    WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
        !isSystemInDarkTheme()
    this.statusBarColor = MaterialTheme.colors.primary.toArgb()
}
