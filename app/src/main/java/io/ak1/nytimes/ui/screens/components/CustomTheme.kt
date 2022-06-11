package io.ak1.nytimes.ui.screens.components

import android.content.res.Configuration
import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import io.ak1.nytimes.utility.dataStore
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.themePreferenceKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Composable
fun isSystemInDarkThemeCustom(): Boolean {

    val context = LocalContext.current
    val prefs = runBlocking { context.dataStore.data.first() }
    return when (context.isDarkThemeOn()
        .collectAsState(initial = prefs[themePreferenceKey] ?: 0).value) {
        2 -> true
        1 -> false
        else -> context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}

@Composable
fun Window.StatusBarConfig(darkTheme: Boolean) {
    WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
        !darkTheme
    this.statusBarColor = MaterialTheme.colors.primary.toArgb()
}