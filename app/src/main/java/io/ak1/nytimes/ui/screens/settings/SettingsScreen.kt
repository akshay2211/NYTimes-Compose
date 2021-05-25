package io.ak1.nytimes.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.screens.components.DefaultAppBar
import io.ak1.nytimes.ui.screens.home.StoriesViewModel

// TODO: 25/05/21 add day-night theme & menu settings
@Composable
fun SettingsScreen(liveModel: StoriesViewModel, navController: NavController) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(titleId = R.string.settings_title, navController = navController)
        }
    ) {}
}