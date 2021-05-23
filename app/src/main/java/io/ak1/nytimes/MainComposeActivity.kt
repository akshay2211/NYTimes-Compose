package io.ak1.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import io.ak1.nytimes.ui.screens.components.RootComponent
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.utility.getScreenSize
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.setUpStatusNavigationBarColors
import org.koin.android.ext.android.inject


class MainComposeActivity : ComponentActivity() {
    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.setUpStatusNavigationBarColors(
                isDarkThemeOn(),
                ContextCompat.getColor(this, io.ak1.nytimes.R.color.background)
            )
            resources.displayMetrics.getScreenSize()
            RootComponent(liveViewModel, window)
        }

    }
}
// TODO: 21/05/21 add Bookmarking page
// TODO: 21/05/21 add settings page