package com.interview.thenewyorktimes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.interview.thenewyorktimes.utility.getScreenSize
import com.interview.thenewyorktimes.utility.isDarkThemeOn
import com.interview.thenewyorktimes.utility.setNavigation
import com.interview.thenewyorktimes.utility.setUpStatusNavigationBarColors
import kotlinx.android.synthetic.main.activity_main.*
/**
 * [MainActivity] first activity for user interaction
 * contains multiple fragments and TabLayout for its navigation
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setUpStatusNavigationBarColors(
            isDarkThemeOn(),
            ContextCompat.getColor(this, R.color.background)
        )
        resources.displayMetrics.getScreenSize()
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottom_navigation.setNavigation(navController)
    }
}