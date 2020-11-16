package com.interview.thenewyorktimes.data.repository

//import com.interview.thenewyorktimes.data.local.AppDatabase
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.data.local.AppDatabase
import com.interview.thenewyorktimes.data.remote.ApiList
import com.interview.thenewyorktimes.model.BaseData
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.utility.LiveDataCollection
import com.interview.thenewyorktimes.utility.NetworkState
import com.interview.thenewyorktimes.utility.extractMessage
import com.interview.thenewyorktimes.utility.resultsToBookmarks
import kotlinx.coroutines.CoroutineScope
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
    var context: Context,
    var db: AppDatabase,
    var apiList: ApiList,
    var coroutineContext: CoroutineContext
) {

    /**
     * Inserts the response into the database.
     */
    private fun insertResultIntoDb(section: String, body: BaseData?) {

        body!!.results.let { stories ->
            var list = stories.map {
                Log.e("check ", "iterations of stories $section ${it.title} ")
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
                    it.abstract_text
                )
            }
            Log.e("check ", "size of stories ${stories.size} ${list.size}")
            db.runInTransaction {

                CoroutineScope(this.coroutineContext).launch {
                    db.resultsDao().insert(list)
                }
            }
        }
    }


    fun refresh(search_content: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        CoroutineScope(this.coroutineContext).launch {
            try {


                var response = apiList.getStories(search_content)
                if (!response.isSuccessful) {
                    var error = response.errorBody()
                    networkState.value = NetworkState.error(error?.extractMessage())
                } else {
                    db.resultsDao().deleteBySectionType(search_content)
                    insertResultIntoDb(search_content, response.body())
                    // since we are in bg thread now, post the result.
                    networkState.postValue(NetworkState.LOADED)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return networkState
    }


    fun getStories(type: String): LiveDataCollection<Results> {
        var dao = db.resultsDao()

        val networkState = MutableLiveData<NetworkState>()
        Log.e("work on ", "first")
        CoroutineScope(this.coroutineContext).launch {
            Log.e("work on ", "second")
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
            }
        }
        Log.e("work on ", "third")
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = refreshTrigger.switchMap {
            refresh(type)
        }
        return LiveDataCollection(
            pagedList = dao.storiesByType(type),
            networkState = networkState,
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState
        )
    }

    fun storeBookMark(results: Results, result: (Boolean) -> Unit) {
        CoroutineScope(this.coroutineContext).launch {
            var bookmark = db.bookmarksDao().getBookmarksById(results.id)
            if (bookmark != null) {
                db.resultsDao().insert(results.apply { bookmarked = false })
                db.bookmarksDao().deleteById(results.id)
                result(false)
            } else {
                db.resultsDao().insert(results.apply { bookmarked = true })
                db.bookmarksDao().insert(results.resultsToBookmarks())
                result(true)
            }
        }
    }

}


