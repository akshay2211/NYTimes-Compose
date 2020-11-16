package com.interview.thenewyorktimes.utility

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import androidx.fragment.app.FragmentActivity
import com.interview.thenewyorktimes.model.Bookmarks
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.ui.singlepage.SingleActivity
import okhttp3.ResponseBody
import org.json.JSONObject


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState private constructor(
    val State: State,
    val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(State.SUCCESS)
        val LOADING = NetworkState(State.RUNNING)
        fun error(msg: String?) = NetworkState(State.FAILED, msg)
    }
}

object ScreenDimensions {
    var HeightPX = 0
    var WidthPX = 0
    var DENSITY = 0f
}

//extracts the error message from the errorbody from response
fun ResponseBody?.extractMessage(): String? {
    if (this == null) {
        return ""
    }
    return try {
        JSONObject(string()).getJSONObject("error").getString("message")
    } catch (e: Exception) {
        "Something went wrong"
    }
}


//retrieve screen density and size in pixels
fun DisplayMetrics.getScreenSize() {
    ScreenDimensions.HeightPX = heightPixels
    ScreenDimensions.WidthPX = widthPixels
    ScreenDimensions.DENSITY = density
}

//clears database after a specific time of 45 minutes
fun Context.clearDatabaseAfterTTL() {
    /*WorkManager.getInstance(this).enqueueUniqueWork(
        "clearDatabase", ExistingWorkPolicy.REPLACE,
        OneTimeWorkRequestBuilder<ClearDatabase>().setInitialDelay(45, TimeUnit.MINUTES)
            .addTag("clearDatabase1")
            .build()
    )*/
}

val Int.dp: Int
    get() = if (this == 0) 0 else kotlin.math.floor(ScreenDimensions.DENSITY * this.toDouble())
        .toInt()

fun Int.getDesiredHeight(width: Int = ScreenDimensions.WidthPX, actualWidth: Int): Int {
    return if (actualWidth == 0) {
        0
    } else {
        (this * width) / actualWidth
    }
}

fun FragmentActivity.startSinglePageActivity(
    result: Results? = null,
    bookmarks: Bookmarks? = null
) {
    var bookmark = if (result == null && bookmarks == null) {
        throw NullPointerException("Both the objects con not be null")
    } else result?.resultsToBookmarks() ?: bookmarks
    startActivity(Intent(this, SingleActivity::class.java).apply { putExtra("bookmark", bookmark) })
}


fun Results.resultsToBookmarks(): Bookmarks {
    return Bookmarks().apply {
        this.title = this@resultsToBookmarks.title
        this.url = this@resultsToBookmarks.url
        this.published_date = this@resultsToBookmarks.published_date
        this.url_thumb = this@resultsToBookmarks.url_thumb
        this.url_large = this@resultsToBookmarks.url_large
        this.type = this@resultsToBookmarks.type
        this.height = this@resultsToBookmarks.height
        this.width = this@resultsToBookmarks.width
        this.id = this@resultsToBookmarks.id
        this.abstract_text = this@resultsToBookmarks.abstract_text
    }
}

