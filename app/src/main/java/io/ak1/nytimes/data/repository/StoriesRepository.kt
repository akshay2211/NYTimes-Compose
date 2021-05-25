package io.ak1.nytimes.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import io.ak1.nytimes.R
import io.ak1.nytimes.data.local.AppDatabase
import io.ak1.nytimes.data.remote.ApiList
import io.ak1.nytimes.model.Bookmarks
import io.ak1.nytimes.model.LiveDataCollection
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.model.StoriesResponse
import io.ak1.nytimes.utility.NetworkState
import io.ak1.nytimes.utility.extractMessage
import io.ak1.nytimes.utility.toBookmarks
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
    // TODO: 21/05/21 remove unnecessary methods and functionalities

    /**
     * Inserts the response into the database.
     */
    private fun insertResultIntoDb(section: String, body: StoriesResponse?) {

        body!!.results.let { stories ->
            val list = stories.map {
                var height = 0
                var width = 0
                var urlMain = ""
                var urlThumb = ""
                if (!it.multimedia.isNullOrEmpty()) {
                    height = it.multimedia[0].height
                    width = it.multimedia[0].width
                    urlMain = it.multimedia[0].url ?: ""
                    urlThumb = it.multimedia[2].url ?: ""
                }


                Results(
                    it.title,
                    it.url,
                    it.published_date,
                    urlThumb,
                    urlMain,
                    section,
                    height,
                    width,
                    it.byline,
                    it.des_facet.joinToString { it },
                    it.abstract_text
                )
            }
            db.runInTransaction {
                CoroutineScope(this.coroutineContext).launch {
                    Log.e("list", "-> ${list[0].des_facet}")
                    db.resultsDao().insert(list)
                }
            }
        }
    }

    suspend fun deleteStories(type: String) = db.resultsDao().deleteBySectionType(type)
    fun getLocalStory(postId: Int): LiveData<Results> = db.resultsDao().getStoriesById(postId)
    fun getBookmarks(): LiveData<List<Bookmarks>> = db.bookmarksDao().getBookmarks()

    fun getStories(type: String): LiveDataCollection<Results> {
        Log.e("retrieving", "stories for $type")
        val dao = db.resultsDao()
        val networkState = MutableLiveData<NetworkState>()
        networkState.postValue(NetworkState.LOADING)
        val refreshTrigger = MutableLiveData<Unit?>()
        val refreshState = refreshTrigger.switchMap {
            refresh(type)
        }
        CoroutineScope(this.coroutineContext).launch {
            if (dao.getCount(type) == 0) {
                networkState.postValue(NetworkState.LOADING)
                try {
                    val response = apiList.getStories(type)
                    if (!response.isSuccessful) {
                        val error = response.errorBody()
                        networkState.postValue(NetworkState.error(error?.extractMessage()))
                        return@launch
                    }
                    insertResultIntoDb(type, response.body())
                    networkState.postValue(NetworkState.LOADED)
                } catch (e: SSLException) {
                    e.printStackTrace()
                    networkState.postValue(NetworkState.error(context.resources.getString(R.string.system_call_error)))
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    networkState.postValue(NetworkState.error(context.resources.getString(R.string.internet_error)))
                } catch (e: Exception) {
                    e.printStackTrace()
                    networkState.postValue(NetworkState.error(e.localizedMessage))
                }
            } else {
                networkState.postValue(NetworkState.LOADED)

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

    fun storeBookMark(results: Results, result: (Boolean) -> Unit) {
        CoroutineScope(this.coroutineContext).launch {
            val bookmark = db.bookmarksDao().getBookmarksById(results.id)
            if (bookmark != null) {
                db.resultsDao().insert(results.apply { bookmarked = false })
                db.bookmarksDao().deleteById(results.id)
                result(false)
            } else {
                db.resultsDao().insert(results.apply { bookmarked = true })
                db.bookmarksDao().insert(results.toBookmarks())
                result(true)
            }
        }
    }

    fun checkBookmarked(title: String): LiveData<Boolean> = db.bookmarksDao().contains(title)
    suspend fun deleteBookmark(title: String) = db.bookmarksDao().deleteByTitle(title)

    suspend fun addBookmark(bookmark: Bookmarks) = db.bookmarksDao().insert(bookmark)

    fun refresh(type: String): LiveData<NetworkState> {
        Log.e("refreshing", "stories for $type")
        val networkState = MutableLiveData<NetworkState>()
        CoroutineScope(this.coroutineContext).launch {
            networkState.postValue(NetworkState.LOADING)
            try {

                val response = apiList.getStories(type)
                if (!response.isSuccessful) {
                    val error = response.errorBody()
                    networkState.postValue(NetworkState.LOADED)
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
}


