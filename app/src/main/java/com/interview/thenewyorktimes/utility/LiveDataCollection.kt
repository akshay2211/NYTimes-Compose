package com.interview.thenewyorktimes.utility

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import com.interview.thenewyorktimes.model.Results

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
data class LiveDataCollection<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<List<T>>? = null,
    // represents the network request status to show to the user
    val networkState: LiveData<NetworkState>? = null,
    val networkState2: MutableState<NetworkState>? = null,
    val pagedList2: MutableState<List<Results>>? = null
)