package io.ak1.nytimes.model

import androidx.lifecycle.LiveData
import io.ak1.nytimes.utility.NetworkState

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
data class LiveDataCollection<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<List<T>>,
    // represents the network request status to show to the user
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit
)