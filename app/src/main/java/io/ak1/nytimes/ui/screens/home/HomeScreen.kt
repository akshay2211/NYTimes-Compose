package io.ak1.nytimes.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ak1.nytimes.ui.screens.components.CustomAppBar
import io.ak1.nytimes.ui.screens.components.CustomTabBar
import io.ak1.nytimes.ui.screens.components.PostElement
import io.ak1.nytimes.utility.NetworkState
import io.ak1.nytimes.utility.State

val mainType = mutableStateOf("home")
val tempIndex = mutableStateOf(0)


@Composable
fun HomeScreenComposable(
    listState: LazyListState,
    viewModel: StoriesViewModel,
    navController: NavController
) {
    //val bookmarks = liveViewModel.getBookmarks ?: HashMap()
    val coroutineScope = rememberCoroutineScope()
    val stories = viewModel.getStories(mainType.value.toLowerCase())

    val resultList = stories.pagedList.observeAsState(initial = listOf())
    val networkState = stories.networkState.observeAsState(initial = NetworkState.LOADING)
    val refreshState = stories.refreshState.observeAsState(initial = NetworkState.LOADED)
    var swipestate = rememberSaveable {
        mutableStateOf(false)
    }

    // TODO: 20/05/21 regain same post after returning from single post screen

    Scaffold(
        topBar = { CustomAppBar(viewModel, navController) }
    ) {
        swipestate.value = refreshState.value.state != State.SUCCESS
        Column {
            CustomTabBar(listState)
            Log.e("start", "-> ${listState.firstVisibleItemIndex}")
            Log.e("State", "-> ${networkState.value.state.name}")
            // TODO: 22/05/21 loading state not getting called on clearing database
            // TODO: 22/05/21 add shimmer on loading
            when (networkState.value.state) {

                State.RUNNING -> {
                }
                State.SUCCESS -> {
                }
                State.FAILED -> {
                }
            }

            SwipeRefresh(
                modifier = Modifier.padding(0.dp, 0.dp),
                state = rememberSwipeRefreshState(swipestate.value),
                onRefresh = {
                    stories.refresh.invoke()


                },
            ) {
                //      Log.e("refresh status", "->  ${refreshState.value.state == State.RUNNING}")

                LazyColumn(state = listState) {

                    itemsIndexed(resultList.value) { pos, element ->

                        Log.e("check pos", "->  ${pos}")

                        PostElement(element, viewModel) { result ->
                            navController.navigate("post/${result.id}")
                        }
                    }
                }
            }
            Log.e("end", "-> ${listState.firstVisibleItemIndex}")


        }

    }
}



