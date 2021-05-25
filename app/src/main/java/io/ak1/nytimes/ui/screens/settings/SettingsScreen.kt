package io.ak1.nytimes.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.screens.components.DefaultAppBar
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.utility.dataStore
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.themePreferenceKey
import kotlinx.coroutines.launch

// TODO: 25/05/21 add day-night theme & menu settings

@Composable
fun SettingsScreen(liveModel: StoriesViewModel, navController: NavController) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(titleId = R.string.settings_title, navController = navController)
        }
    ) {
        val context = LocalContext.current
        var theme = context.isDarkThemeOn().collectAsState(initial = 0)

        val coroutineScope = rememberCoroutineScope()

        Row(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Column(modifier = Modifier.weight(1f, true)) {
                Text(
                    "theme",
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "result",
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                )
            }
            Switch(checked = theme.value == 1, onCheckedChange = {
                coroutineScope.launch {
                    context.dataStore.edit { settings ->
                        Log.e("checked ", "boolean $it")
                        settings[themePreferenceKey] = if (it) 1 else 2
                    }
                }
            })
        }
    }
}