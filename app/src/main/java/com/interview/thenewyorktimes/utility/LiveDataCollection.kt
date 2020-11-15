package com.interview.thenewyorktimes.utility

import androidx.lifecycle.LiveData

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
data class LiveDataCollection<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<List<T>>,
    // represents the network request status to show to the user
    val networkState: LiveData<NetworkState>,
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    val refreshState: LiveData<NetworkState>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit
)