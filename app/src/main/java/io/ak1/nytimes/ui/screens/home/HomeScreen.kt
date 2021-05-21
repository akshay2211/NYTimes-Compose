package io.ak1.nytimes.ui.screens.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.components.CustomAppBar
import io.ak1.nytimes.ui.screens.components.CustomTabBar
import io.ak1.nytimes.ui.screens.components.PostElement
import kotlinx.coroutines.launch

val mainType = mutableStateOf("home")
val tempIndex = mutableStateOf(0)


@Composable
fun HomeScreenComposable(
    pos1: MutableState<Int>,
    listState: LazyListState,
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val resultList = liveViewModel.getStories(mainType.value.toLowerCase())
        .pagedList.observeAsState(initial = listOf()).value
    var swipestate = rememberSaveable {
        mutableStateOf(false)
    }
    // TODO: 20/05/21 regain same post after returing from single post screen

    Scaffold(
        topBar = { CustomAppBar() }
    ) {
        Column {
            CustomTabBar(listState)


            Log.e("start", "-> ${listState.firstVisibleItemIndex}")

            SwipeRefresh(
                modifier = Modifier.padding(0.dp, 0.dp),
                state = rememberSwipeRefreshState(swipestate.value),
                onRefresh = {
                    swipestate.value = true

                    Handler(Looper.getMainLooper()).postDelayed({
                        Log.e("refresh called", "from inside")
                        swipestate.value = false
                    }, 2000L)
                    liveViewModel.deleteStories(mainType.value.toLowerCase(), coroutineScope)
                    //listState.scrollToItem(pos1.value)
                },
            ) {
                LazyColumn(state = listState) {

                    itemsIndexed(resultList) { pos, element ->
                        PostElement(element) { result ->
                            navController.navigate("post/${result.id}")
                        }
                    }
                    if (!listState.isScrollInProgress && resultList.isNotEmpty()) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(listState.firstVisibleItemIndex)
                        }
                    }

                }
                LaunchedEffect(listState) {
                    Log.e("check always", "->  ${listState.firstVisibleItemIndex} ${pos1.value}")
                    coroutineScope.launch {
                        if (resultList.isNotEmpty())
                            listState.animateScrollToItem(pos1.value)
                    }
                }
            }
            Log.e("end", "-> ${listState.firstVisibleItemIndex}")


        }

    }
}



