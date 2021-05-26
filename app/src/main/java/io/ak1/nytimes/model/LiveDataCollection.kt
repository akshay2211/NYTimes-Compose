package io.ak1.nytimes.model

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
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit
)

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState internal constructor(
    val state: State,
    val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(State.SUCCESS)
        val LOADING = NetworkState(State.RUNNING)
        fun error(msg: String?) = NetworkState(State.FAILED, msg)
    }
}