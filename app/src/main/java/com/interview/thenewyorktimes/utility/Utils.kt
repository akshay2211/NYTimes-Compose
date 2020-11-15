package com.interview.thenewyorktimes.utility

import android.content.Context
import android.util.DisplayMetrics
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
    return (this * width) / actualWidth
}



