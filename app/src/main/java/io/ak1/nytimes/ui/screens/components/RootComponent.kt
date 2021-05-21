package io.ak1.nytimes.ui.screens.components

import android.util.Log
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.home.HomeScreenComposable
import io.ak1.nytimes.ui.screens.post.PostScreenComposable
import io.ak1.nytimes.ui.theme.TheNewYorkTimesAppTheme


@Composable
fun RootComponent(liveViewModel: StoriesViewModel, window: Window) {
    TheNewYorkTimesAppTheme {
        val listState = rememberLazyListState()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            !isSystemInDarkTheme()
        window.statusBarColor = MaterialTheme.colors.primary.toArgb()
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()

            var clickPos = remember {
                mutableStateOf(0)
            }
            NavHost(
                navController = navController,
                startDestination = "eng"
            ) {
                composable("eng") {
                    HomeScreenComposable(clickPos, listState, liveViewModel, navController) {
                    }
                }
                composable(
                    "post/{postId}",
                    arguments = listOf(navArgument("postId") { type = NavType.IntType })
                ) {
                    val arguments = requireNotNull(it.arguments)
                    val postId = arguments.getInt("postId")
                    Log.e("saved state", "->  ${listState.firstVisibleItemIndex}")
                    PostScreenComposable(postId, liveViewModel, navController) {

                    }
                }
            }
        }
    }
}
