package io.ak1.nytimes.ui.screens.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.screens.components.CustomAlertDialog
import io.ak1.nytimes.ui.screens.components.DefaultAppBar
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
import io.ak1.nytimes.utility.dataStore
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.themePreferenceKey
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(liveModel: StoriesViewModel, navController: NavController) {

    // Default is false (not showing)
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(titleId = R.string.settings_title, navController = navController)
        }
    ) {
        val context = LocalContext.current
        val theme = context.isDarkThemeOn().collectAsState(initial = 0)

        val coroutineScope = rememberCoroutineScope()
        Column(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .width(600.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp, 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Column(modifier = Modifier.weight(1f, true)) {
                        Text(
                            "Theme",
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            stringResource(
                                id =
                                when (theme.value) {
                                    0 -> R.string.default_theme
                                    else -> R.string.custom_theme
                                }
                            ),
                            style = MaterialTheme.typography.overline,
                            textAlign = TextAlign.Start,
                        )
                    }
                    Switch(
                        colors = SwitchDefaults.colors(uncheckedThumbColor = MaterialTheme.colors.secondaryVariant),
                        onCheckedChange = {
                            coroutineScope.launch {
                                context.dataStore.edit { settings ->
                                    settings[themePreferenceKey] = if (it) 1 else 0
                                }
                            }
                        }, checked = theme.value != 0
                    )
                }
                if (theme.value != 0) {

                    Row(
                        modifier = Modifier
                            .padding(16.dp, 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {

                        Column(modifier = Modifier.weight(1f, true)) {
                            Text(
                                "Dark Mode",
                                style = MaterialTheme.typography.h6,
                                maxLines = 2,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                stringResource(
                                    id =
                                    when (theme.value) {
                                        1 -> R.string.light_mode_selected
                                        2 -> R.string.dark_mode_selected
                                        else -> R.string.default_theme
                                    }
                                ),
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Start,
                            )
                        }
                        Switch(
                            colors = SwitchDefaults.colors(uncheckedThumbColor = MaterialTheme.colors.secondaryVariant),
                            onCheckedChange = {
                                coroutineScope.launch {
                                    context.dataStore.edit { settings ->
                                        Log.e("checked ", "boolean $it")
                                        settings[themePreferenceKey] = if (it) 2 else 1
                                    }
                                }
                            }, checked = theme.value == 2
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .clickable {
                            setShowDialog(true)
                        }
                        .padding(16.dp, 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Column(modifier = Modifier.weight(1f, true)) {
                        Text(
                            stringResource(
                                id = R.string.cache_title
                            ),
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            stringResource(
                                id = R.string.cache_summary
                            ),
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Start,
                        )
                    }
                }
                CustomAlertDialog(showDialog = showDialog, setShowDialog = setShowDialog) {
                    liveModel.deleteAll()
                    Toast.makeText(context, R.string.storage_cleared, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
