package io.ak1.nytimes.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ak1.nytimes.R
import io.ak1.nytimes.model.NetworkState
import io.ak1.nytimes.model.State
import io.ak1.nytimes.ui.screens.components.*
import io.ak1.nytimes.ui.screens.navigation.MainDestinations
import java.util.*

val mainType = mutableStateOf("home")
val tempIndex = mutableStateOf(0)


@Composable
fun HomeScreenComposable(
    listState: LazyListState,
    viewModel: StoriesViewModel,
    navController: NavController
) {
    val stories = viewModel.getStories(mainType.value.toLowerCase(Locale.getDefault()))
    val resultList = stories.pagedList.observeAsState(initial = listOf())
    val networkState = stories.networkState.observeAsState(initial = NetworkState.LOADING)
    val refreshState = stories.refreshState.observeAsState(initial = NetworkState.LOADED)
    val swipeState = rememberSaveable {
        mutableStateOf(false)
    }


    // TODO: 24/05/21 add status for 429 Too Many Requests
    Scaffold(
        topBar = { HomeAppBar(navController) }
    ) {
        swipeState.value = refreshState.value == NetworkState.LOADING
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTabBar(listState)
            SwipeRefresh(
                state = rememberSwipeRefreshState(swipeState.value),
                onRefresh = {
                    stories.refresh.invoke()
                },
            ) {
                when (networkState.value.state) {
                    State.RUNNING -> {
                        ShimmerPlaceholder()
                    }
                    State.SUCCESS -> {
                        LazyColumn(state = listState) {
                            itemsIndexed(resultList.value) { _, element ->
                                PostElement(element, viewModel) { result ->
                                    navController.navigate("${MainDestinations.POST_ROUTE}/${result.id}")
                                }
                            }
                        }
                    }
                    State.FAILED -> {
                        PlaceHolder(R.drawable.ic_undraw_not_found_60pq, R.string.internet_error)
                    }
                }

            }
        }

    }
}




