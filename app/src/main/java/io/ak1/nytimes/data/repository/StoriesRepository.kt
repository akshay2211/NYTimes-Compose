package io.ak1.nytimes.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import io.ak1.nytimes.R
import io.ak1.nytimes.data.local.AppDatabase
import io.ak1.nytimes.data.remote.ApiList
import io.ak1.nytimes.model.*
import io.ak1.nytimes.utility.extractMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import kotlin.coroutines.CoroutineContext


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

/**
 * in [StoriesRepository] the lists retrieved from remote api is first stored in local database
 */
class StoriesRepository(
    private val context: Context,
    private val db: AppDatabase,
    private val apiList: ApiList,
    private val coroutineContext: CoroutineContext
) {
    /**
     * Inserts the response into the database.
     */
    private fun insertResultIntoDb(section: String, body: StoriesResponse?) {

        body!!.results.let { stories ->
            val list = stories.map {
                var height = 0
                var width = 0
                var urlMain = ""
                if (!it.multimedia.isNullOrEmpty()) {
                    height = it.multimedia[0].height
                    width = it.multimedia[0].width
                    urlMain = it.multimedia[0].url ?: ""
                }


                Results(
                    title = it.title,
                    shortUrl = it.shortUrl,
                    publishedDate = it.publishedDate,
                    urlLarge = urlMain,
                    type = section,
                    height = height,
                    width = width,
                    byline = it.byline,
                    desFacet = it.desFacet.joinToString { it },
                    abstractText = it.abstractText
                )
            }
            db.runInTransaction {
                CoroutineScope(this.coroutineContext).launch {
                    Log.e("list", "-> ${list[0].desFacet}")
                    db.resultsDao().insert(list)
                }
            }
        }
    }

    private suspend fun deleteStories(type: String) = db.resultsDao().deleteBySectionType(type)
    fun getLocalStory(postId: Int): LiveData<Results> = db.resultsDao().getStoriesById(postId)
    val getBookmarks: LiveData<List<Bookmarks>> = db.bookmarksDao().getBookmarks()

    fun getStories(type: String): LiveDataCollection<Results> {
        val dao = db.resultsDao()
        val networkState = MutableLiveData<NetworkState>()
        val refreshTrigger = MutableLiveData<Unit?>()
        val refreshState = refreshTrigger.switchMap {
            refresh(type)
        }
        CoroutineScope(this.coroutineContext).launch {
            when {
                dao.getCount(type, 0) == 0 -> {
                    networkState.postValue(NetworkState.LOADING)
                    getResponse(type) {
                        networkState.postValue(it)
                    }
                }
                dao.getCount(type) == 0 -> {
                    getResponse(type) {
                        networkState.postValue(it)
                    }
                }
                else -> {
                    networkState.postValue(NetworkState.LOADED)

                }
            }
        }

        return LiveDataCollection(
            pagedList = dao.storiesByType(type),
            networkState = networkState,
            refreshState = refreshState,
            refresh = {
                refreshTrigger.postValue(null)
            },
        )
    }

    private suspend fun getResponse(type: String, function: (NetworkState) -> Unit) {
        try {
            val response = apiList.getStories(type)
            if (!response.isSuccessful) {
                val error = response.errorBody()
                function(NetworkState.error(error?.extractMessage()))
                return
            }
            insertResultIntoDb(type, response.body())
            function(NetworkState.LOADED)
        } catch (e: SSLException) {
            e.printStackTrace()
            function(NetworkState.error(context.resources.getString(R.string.system_call_error)))
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            function(NetworkState.error(context.resources.getString(R.string.internet_error)))
        } catch (e: Exception) {
            e.printStackTrace()
            function(NetworkState.error(e.localizedMessage))
        }
    }

    fun checkBookmarked(title: String): LiveData<Boolean> = db.bookmarksDao().contains(title)
    suspend fun deleteBookmark(title: String) = db.bookmarksDao().deleteByTitle(title)

    suspend fun addBookmark(bookmark: Bookmarks) = db.bookmarksDao().insert(bookmark)

    private fun refresh(type: String): LiveData<NetworkState> {
        Log.e("refreshing", "stories for $type")
        val networkState = MutableLiveData<NetworkState>()
        CoroutineScope(this.coroutineContext).launch {
            networkState.postValue(NetworkState.LOADING)
            try {

                val response = apiList.getStories(type)
                if (!response.isSuccessful) {
                    val error = response.errorBody()
                    networkState.postValue(NetworkState.error(error.extractMessage()))
                    return@launch
                }
                deleteStories(type)
                insertResultIntoDb(type, response.body())
                delay(1000L)
                networkState.postValue(NetworkState.LOADED)
            } catch (e: Exception) {
                e.printStackTrace()
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

        }
        return networkState
    }

    fun deleteAll() {
        CoroutineScope(this.coroutineContext).launch {
            db.resultsDao().deleteTable()
            db.bookmarksDao().deleteTable()
        }
    }

    fun getLocalBookmark(postId: Int) = db.bookmarksDao().getBookmarksById(postId)
}


