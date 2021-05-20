package io.ak1.nytimes.ui.screens.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.home.components.CustomAppBar
import io.ak1.nytimes.ui.screens.home.components.CustomTabBar
import io.ak1.nytimes.ui.screens.home.components.PostElement

val mainType = mutableStateOf("home")
val tempIndex = mutableStateOf(0)


@Composable
fun HomeScreenComposable(
    listState: LazyListState,
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {

    val elements = liveViewModel.getStories(mainType.value.toLowerCase()).pagedList.observeAsState(
        initial = listOf()
    )

    Scaffold(
        topBar = { CustomAppBar() }
    ) {
        Column {
            CustomTabBar(listState)
            ShowNotes(listState, elements) {
                navController.navigate("kor")
                callback(it.title ?: " sample ")
            }

        }
    }
}


@Composable
fun ShowNotes(
    listState: LazyListState,
    resultList: State<List<Results>>,
    clickCallback: (Results) -> Unit
) {
    var swipestate = remember {
        false
    }
    SwipeRefresh(
        modifier = Modifier.padding(0.dp, 0.dp),
        state = rememberSwipeRefreshState(swipestate),
        onRefresh = {
            swipestate = true

            Handler(Looper.getMainLooper()).postDelayed({
                Log.e("refresh called", "from inside")
                swipestate = false
            }, 1000L)
        },
    ) {
        LazyColumn(state = listState) {
            items(resultList.value) {
                PostElement(it) { result ->
                    clickCallback(result)
                }
            }
        }
    }

}
