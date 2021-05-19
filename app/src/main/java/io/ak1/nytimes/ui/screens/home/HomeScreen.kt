package io.ak1.nytimes.ui.screens.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.home.StoriesViewModel

private val types = arrayOf(
    "home",
    "arts",
    "automobiles",
    /* "books",
     "business",
     "fashion",
     "food",
     "health",
     "home",
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
     "world"*/
)
val mainType = mutableStateOf("home")

@Composable
fun HomeScreenComposable(
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {


    Column {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = 0,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                Log.e("tabPositions", "->  $tabPositions")
                /* TabRowDefaults.Indicator(
                    // Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                 )*/
            }
        ) {
            var tempIndex = 0
            // Add tabs for all of our pages
            // TODO: 19/05/21 tabs needs to be managed
            types.forEachIndexed { index, title ->
                Log.e("title", "$title")
                Tab(
                    text = { Text("" + title.toUpperCase()) },
                    selected = tempIndex == index,
                    onClick = {
                        tempIndex = index
                        mainType.value = title
                    }
                )
            }
        }
        // TODO: 19/05/21 only one type of stories should be displayed
        ShowNotes(
            liveViewModel.getStories(mainType.value.toLowerCase()).pagedList.observeAsState(
                initial = listOf()
            )
        ) {
            navController.navigate("kor")
            callback(it.title ?: " sample ")
        }

    }

/*

    when (val uiState = viewModel.categoryListState.value) {
        is CategoryListViewModel.UIState.Error -> CategoryError()
        is CategoryListViewModel.UIState.Loading -> CategoryLoading()
        is CategoryListViewModel.UIState.Success -> CategoryList(
            categories = uiState.categoryList,
            onCategoryClicked
        )
    }*/


}


@Composable
fun ShowNotes(resultList: State<List<Results>>, clickCallback: (Results) -> Unit) {
    var swipestate = remember {
        false
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(swipestate),
        onRefresh = {
            swipestate = true

            Handler(Looper.getMainLooper()).postDelayed({
                Log.e("refresh called", "from inside")
                swipestate = false
            }, 1000L)
        },
    ) {
        LazyColumn {
            items(resultList.value) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { clickCallback(it) }) {
                    Log.e("url ", "-> ${it.url}")
                    Card(elevation = 5.dp, modifier = Modifier.padding(0.dp, 16.dp)) {
                        Image(
                            painter = rememberCoilPainter(
                                request = it.url_large,
                                previewPlaceholder = R.drawable.gradient_bottom,
                                fadeIn = true
                            ),
                            contentDescription = it.title ?: "empty",
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                }
                Text(
                    text = it.title ?: "empty",
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 0.dp, 18.dp)
                        .fillMaxWidth()
                )
            }
        }
    }

}
