package io.ak1.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.ak1.nytimes.ui.screens.components.RootComponent
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            RootComponent(liveViewModel, window)
        }

    }
}