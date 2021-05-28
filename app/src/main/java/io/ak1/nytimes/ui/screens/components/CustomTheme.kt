package io.ak1.nytimes.ui.screens.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import io.ak1.nytimes.utility.dataStore
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.themePreferenceKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Composable
fun isSystemInDarkThemeCustom(): Boolean {

    val context = LocalContext.current
    val exampleData = runBlocking { context.dataStore.data.first() }
    val theme =
        context.isDarkThemeOn().collectAsState(initial = exampleData[themePreferenceKey] ?: 0)
    Log.e("isDarkThemeOn", "collectAsState  ${theme.value}")
    return when (theme.value) {
        2 -> true
        1 -> false
        else -> context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}