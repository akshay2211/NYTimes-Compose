package com.interview.thenewyorktimes.ui.screens.home

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.ui.home.StoriesViewModel


@Composable
fun HomeScreenComposable(
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {/*

    when (val uiState = viewModel.categoryListState.value) {
        is CategoryListViewModel.UIState.Error -> CategoryError()
        is CategoryListViewModel.UIState.Loading -> CategoryLoading()
        is CategoryListViewModel.UIState.Success -> CategoryList(
            categories = uiState.categoryList,
            onCategoryClicked
        )
    }*/
    ShowNotes(liveViewModel.getStories("Movies").pagedList.observeAsState(initial = listOf())) {
        navController.navigate("kor")
        callback(it.title ?: " sample ")
    }


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
