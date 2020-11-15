package com.interview.thenewyorktimes.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.interview.thenewyorktimes.data.repository.StoriesRepository

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class HomeViewModel(val handle: SavedStateHandle, val storiesRepo: StoriesRepository) :
    ViewModel() {
    companion object {
        const val KEY_STORIES = "stories_tabs_key"
        const val DEFAULT_VALUE = "home"
    }

    init {
        if (!handle.contains(KEY_STORIES)) {
            handle.set(KEY_STORIES, DEFAULT_VALUE)
        }
    }

    private val repoResult = handle.getLiveData<String>(KEY_STORIES).map {
        storiesRepo.getStories(DEFAULT_VALUE)
    }
    val posts = repoResult.switchMap { it.pagedList }
    val networkState = repoResult.switchMap { it.networkState }
    val refreshState = repoResult.switchMap { it.refreshState }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun showSectionContent(section_content: String): Boolean {
        if (handle.get<String>(KEY_STORIES) == section_content) {
            return false
        }
        handle.set(KEY_STORIES, section_content)
        return true
    }

}