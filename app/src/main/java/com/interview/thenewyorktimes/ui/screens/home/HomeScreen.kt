package com.interview.thenewyorktimes.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.ui.home.StoriesViewModel

@Composable
fun HomeScreenComposable(
    liveViewModel: StoriesViewModel,
    navController: NavController,
    callback: (String) -> Unit
) {
    liveViewModel.getStories("Movies").pagedList2?.let {
        ShowNotes(it.value)
    }

}


@Composable
fun ShowNotes(resultList: List<Results>) {
    LazyColumn {
        items(resultList) {
            Text(
                text = it.title ?: "empty",
                modifier = Modifier
                    .padding(16.dp, 4.dp, 4.dp, 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}