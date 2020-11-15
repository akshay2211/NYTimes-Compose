package com.interview.thenewyorktimes.utility

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.text.format.DateUtils
import android.view.View
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

fun BottomNavigationView.setNavigation(navController: NavController) {
    NavigationUI.setupWithNavController(
        this,
        navController
    )
    /*this.setOnNavigationItemSelectedListener { item ->
        Log.e("nav", "${navController.currentDestination?.label}")
        when (item.itemId) {
            R.id.page_1 -> {
                if (navController.currentDestination?.label?.equals("fragment_bookmark") == true)
                    navController.popBackStack()
                Log.e("click ", "one")
                // Respond to navigation item 1 click
                true
            }
            R.id.page_2 -> {
                if (navController.currentDestination?.label?.equals("fragment_home") == true)
                    navController.navigate(R.id.action_homeFragment_to_bookmarkFragment)
                Log.e("click ", "two")
                // Respond to navigation item 2 click
                true
            }
            else -> false
        }
    }*/
}


/**
 * extension [setUpStatusNavigationBarColors] to setup color codes
 * and themes according to themes
 */
fun Window.setUpStatusNavigationBarColors(isLight: Boolean = false, colorCode: Int = Color.WHITE) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        statusBarColor = colorCode
        navigationBarColor = colorCode
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        setDecorFitsSystemWindows(isLight)
    } else {
        @Suppress("DEPRECATION")
        decorView.systemUiVisibility = if (isLight) {
            0
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                0
            }
        }
    }
}

/**
 * extension [isDarkThemeOn] checks the saved theme from preference
 * and returns boolean
 */
fun Context.isDarkThemeOn(): Boolean {
    var key = PreferenceManager.getDefaultSharedPreferences(this).getString("list_theme", "1")
    return when (key) {
        "2" -> true
        "1" -> false
        else -> return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}

fun String?.timeAgo(): CharSequence? {
    if (this.isNullOrEmpty()) {
        return ""
    }
    //2020-11-14T05:00:17-05:00
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    var date = inputFormat.parse(this)
    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
    )
}