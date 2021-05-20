package io.ak1.nytimes

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.home.HomeScreenComposable
import io.ak1.nytimes.ui.screens.home.components.PostElement
import io.ak1.nytimes.ui.theme.TheNewYorkTimesAppTheme
import org.koin.android.ext.android.inject

internal val topBarTitle = mutableStateOf("Top bar ")


class MainComposeActivity : ComponentActivity() {
    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComponent(liveViewModel, window)

        }
    }
}


@Composable
fun MainComponent(liveViewModel: StoriesViewModel, window: Window) {
    TheNewYorkTimesAppTheme {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            !isSystemInDarkTheme()
        window.statusBarColor = MaterialTheme.colors.primary.toArgb()
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController().apply {
                topBarTitle.value = this.currentDestination?.label?.toString() ?: ""
            }
            topBarTitle.value = "Home"
            val listState = rememberLazyListState()
            NavHost(
                navController = navController,
                startDestination = "eng"
            ) {
                composable("eng") {
                    HomeScreenComposable(listState, liveViewModel, navController) {
                        topBarTitle.value = it
                    }
                }
                composable(
                    "post/{postId}",
                    arguments = listOf(navArgument("postId") { defaultValue = "0" })
                ) {
                    var data = it.arguments?.getString("postId") ?: "0"

                    Greeting2(data, liveViewModel, navController) {
                        topBarTitle.value = it
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting2(
    postId: String,
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    var story = liveViewModel.getStory(postId).observeAsState(
        initial = null
    )
    PostElement(results = story.value ?: Results()) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = topBarTitle.value, modifier = Modifier
                .fillMaxWidth()

        )
        Image(
            painter = painterResource(R.drawable.ic_bookmark),
            contentDescription = "hie"
        )
        Image(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "hie"
        )

    }
}