package io.ak1.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.components.RootComponent
import org.koin.android.ext.android.inject


class MainComposeActivity : ComponentActivity() {
    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootComponent(liveViewModel, window)
        }

    }
}
// TODO: 21/05/21 add Bookmarking page
// TODO: 21/05/21 add settings page