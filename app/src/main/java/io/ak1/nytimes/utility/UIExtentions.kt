package io.ak1.nytimes.utility

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateUtils
import androidx.core.net.ConnectivityManagerCompat
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */


val themePreferenceKey = intPreferencesKey("list_theme")

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
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
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