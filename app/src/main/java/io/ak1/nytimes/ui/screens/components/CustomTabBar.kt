package io.ak1.nytimes.ui.screens.components


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.ak1.nytimes.ui.screens.home.mainType
import io.ak1.nytimes.ui.screens.home.tempIndex
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun CustomTabBar(listState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = tempIndex.value
    ) {

        // Add tabs for all of our pages
        types.forEachIndexed { index, title ->
            Tab(
                text = { Text("" + title.toUpperCase(Locale.getDefault())) },
                selected = tempIndex.value == index,
                onClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                    tempIndex.value = index
                    mainType.value = title
                }
            )
        }
    }
}


private val types = arrayOf(
    "home",
    "arts",
    "automobiles",
    "books",
    "business",
    "fashion",
    "food",
    "health",
    "insider",
    "magazine",
    "movies",
    "nyregion",
    "obituaries",
    "opinion",
    "politics",
    "realestate",
    "science",
    "sports",
    "sundayreview",
    "technology",
    "theater",
    "t-magazine",
    "travel",
    "upshot",
    "us",
    "world"
)