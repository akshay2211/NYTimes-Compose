package io.ak1.nytimes.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import io.ak1.nytimes.R
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.navigation.MainDestinations

@Composable
fun CustomAppBar(liveViewModel: StoriesViewModel, navController: NavController) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .wrapContentSize()
                .wrapContentHeight()
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .layoutId("ToolBarTitleId")
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_bookmark),
                contentDescription = "hie",
                //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .layoutId("ToolBarBookmarkId")
                    .clickable {
                        navController.navigate(MainDestinations.BOOKMARK_ROUTE)
                    }
                    .padding(12.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "hie",
                modifier = Modifier
                    .layoutId("ToolBarSettingsId")
                    .clickable {
                        navController.navigate(MainDestinations.SETTINGS_ROUTE)
                    }
                    .padding(12.dp)
            )
        }
    }
}