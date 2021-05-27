package io.ak1.nytimes.ui.screens.components

import android.content.res.Configuration
import android.view.Window
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
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
import io.ak1.nytimes.utility.isDarkThemeOn


@Composable
fun RootComponent(viewModel: StoriesViewModel, window: Window) {

    // TODO: 25/05/21 theme management has to be integrated
    val context = LocalContext.current
    val theme = context.isDarkThemeOn().collectAsState(initial = 0)
    val isthemeDark = when (theme.value) {
        2 -> true
        1 -> false
        else -> context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    // context.setupTheme(theme.value)

    TheNewYorkTimesAppTheme(isthemeDark) {
        val listState = rememberLazyListState()
        window.StatusBarConfig(isthemeDark)
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
                    val postId = requireNotNull(it.arguments).getInt(MainDestinations.POST_ID_KEY)
                    PostScreenComposable(postId, viewModel, navController)
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

@Composable
fun Window.StatusBarConfig(darkTheme: Boolean) {
    WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
        !darkTheme
    this.statusBarColor = MaterialTheme.colors.primary.toArgb()
}
