package com.interview.thenewyorktimes.ui.home

import androidx.lifecycle.ViewModel
import com.interview.thenewyorktimes.data.repository.StoriesRepository

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class StoriesViewModel(val storiesRepo: StoriesRepository) : ViewModel() {
    fun getStories(type: String) = storiesRepo.getStories(type)
}