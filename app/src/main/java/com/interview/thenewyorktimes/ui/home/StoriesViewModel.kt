package com.interview.thenewyorktimes.ui.home

import androidx.lifecycle.ViewModel
import com.interview.thenewyorktimes.data.local.AppDatabase
import com.interview.thenewyorktimes.data.repository.StoriesRepository
import com.interview.thenewyorktimes.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class StoriesViewModel(private val storiesRepo: StoriesRepository) : ViewModel() {
    fun getStories(type: String) = storiesRepo.getStories(type)
    fun bookmark(results: Results, result: (Boolean) -> Unit) =
        storiesRepo.storeBookMark(results, result)

}

fun AppDatabase.deleteBookmark(id: Int, coroutineContext: CoroutineContext) {
    CoroutineScope(coroutineContext).async {
        this@deleteBookmark.bookmarksDao().deleteById(id)
    }
}

fun AppDatabase.deleteAll(coroutineContext: CoroutineContext) {
    CoroutineScope(coroutineContext).async {
        this@deleteAll.resultsDao().deleteTable()
    }
}