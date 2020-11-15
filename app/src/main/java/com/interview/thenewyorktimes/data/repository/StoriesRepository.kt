package com.interview.thenewyorktimes.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.data.local.AppDatabase
//import com.interview.thenewyorktimes.data.local.AppDatabase
import com.interview.thenewyorktimes.data.remote.ApiList
import com.interview.thenewyorktimes.model.BaseData
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.utility.LiveDataCollection
import com.interview.thenewyorktimes.utility.NetworkState
import com.interview.thenewyorktimes.utility.extractMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

/**
 * in [StoriesRepository] the paged lists retrieved from remote api is first stored in local database
 * and then emited to the UI via Live data, API's are called by the [CustomBoundaryCallback] when all local data is
 * used.
 */
class StoriesRepository(
    var context: Context,
    var db: AppDatabase,
    var apiList: ApiList,
    var coroutineContext: CoroutineContext
) {

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(section: String, body: BaseData?) {

        body!!.results.let { stories ->
            var list = stories.map {
                Log.e("check ", "iterations of stories $section ${it.title} ")
                var height = 0
                var width = 0
                if (it.multimedia.get(0) != null) {
                    height = it.multimedia.get(0).height
                    width = it.multimedia.get(0).width
                }

                Results(
                    it.title,
                    it.multimedia.get(0).url ?: "",
                    it.published_date,
                    it.multimedia.get(2).url ?: "",
                    section,
                    height,
                    width
                )
            }
            Log.e("check ", "size of stories ${stories.size} ${list.size}")
            db.runInTransaction {
                CoroutineScope(this.coroutineContext).launch {
                    list.forEach {
                        db.resultsDao().insert(it)
                    }

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

    /*

 fun getImgurPosts(search_content: String = "", i: Int): LiveDataCollection<Images> {

     val boundaryCallback = CustomBoundaryCallback(
         context = context,
         apiList = apiList,
         searchedContent = search_content,
         coroutineContext = coroutineContext,
         handleResponse = this::insertResultIntoDb,
         ioExecutor = Executors.newSingleThreadExecutor(),
         networkPageSize = i
     )
     // we are using a mutable live data to trigger refresh requests which eventually calls
     // refresh method and gets a new live data. Each refresh request by the user becomes a newly
     // dispatched data in refreshTrigger
     val refreshTrigger = MutableLiveData<Unit>()
     val refreshState = refreshTrigger.switchMap {
         refresh(search_content)
     }
     val livePagedList = db.imagesDao().postsBySearchContents(search_content).toLiveData(
         pageSize = i,
         boundaryCallback = boundaryCallback
     )

     return LiveDataCollection(
         pagedList = livePagedList,
         networkState = boundaryCallback.networkState,
         retry = {
             boundaryCallback.helper.retryAllFailed()
         },
         refresh = {
             refreshTrigger.value = null
         },
         refreshState = refreshState
     )
 }
*/
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


}


