package io.ak1.nytimes.utility

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateUtils
import androidx.core.net.ConnectivityManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.ak1.nytimes.model.Bookmarks
import io.ak1.nytimes.model.Results
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

const val dateFormatPattern = "yyyy-MM-dd'T'HH:mm:ssZ"

val inputFormat = SimpleDateFormat(dateFormatPattern, Locale.ENGLISH)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val themePreferenceKey = intPreferencesKey("list_theme")

val calenderDate = Calendar.getInstance().apply {
    add(Calendar.HOUR, -2)
}.time.time

/**
 * extension [isDarkThemeOn] checks the saved theme from preference
 * and returns boolean
 */
fun Context.isDarkThemeOn() = dataStore.data
    .map { preferences ->
        // No type safety.
        preferences[themePreferenceKey] ?: 0
    }


/**
 *  retrieves the formatted time as name [timeAgo] suggests
 */
fun String?.timeAgo(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    //2020-11-14T05:00:17-05:00
    val date = inputFormat.parse(this) ?: Date()
    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

// Checks if Network is available
fun Context.isNetworkAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return !ConnectivityManagerCompat.isActiveNetworkMetered(cm)
}

//extracts the error message from the error-body from response
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


/*
//clears database after a specific time of 45 minutes
fun Context.clearDatabaseAfterTTL() {
  WorkManager.getInstance(this).enqueueUniqueWork(
       "clearDatabase", ExistingWorkPolicy.REPLACE,
       OneTimeWorkRequestBuilder<ClearDatabase>().setInitialDelay(45, TimeUnit.MINUTES)
           .addTag("clearDatabase1")
           .build()
   )
}*/


fun Results.toBookmarks(): Bookmarks {
    return Bookmarks().apply {
        this.title = this@toBookmarks.title
        this.shortUrl = this@toBookmarks.shortUrl
        this.publishedDate = this@toBookmarks.publishedDate
        this.urlLarge = this@toBookmarks.urlLarge
        this.type = this@toBookmarks.type
        this.height = this@toBookmarks.height
        this.width = this@toBookmarks.width
        this.id = this@toBookmarks.id
        this.abstractText = this@toBookmarks.abstractText
        this.byline = this@toBookmarks.byline
        this.desFacet = this@toBookmarks.desFacet
    }
}


