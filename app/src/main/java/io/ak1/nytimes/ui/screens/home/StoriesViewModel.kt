package io.ak1.nytimes.ui.screens.home

import androidx.lifecycle.ViewModel
import io.ak1.nytimes.data.repository.StoriesRepository
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.utility.toBookmarks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class StoriesViewModel(private val storiesRepo: StoriesRepository) : ViewModel() {

    // Story
    fun getStory(postId: Int) = storiesRepo.getLocalStory(postId)

    fun getStories(type: String) = storiesRepo.getStories(type)

    // Bookmarks
    fun getBookmark(postId: Int) = storiesRepo.getLocalBookmark(postId)

    val bookmarks = storiesRepo.getBookmarks

    fun isBookmarked(title: String) = storiesRepo.checkBookmarked(title)

    fun deleteBookmark(results: Results, coroutineScope: CoroutineScope) =
        coroutineScope.launch { storiesRepo.deleteBookmark(results.title ?: "") }

    fun addBookmark(results: Results, coroutineScope: CoroutineScope) =
        coroutineScope.launch { storiesRepo.addBookmark(results.toBookmarks()) }

    // Common
    fun deleteAll() = storiesRepo.deleteAll()

}