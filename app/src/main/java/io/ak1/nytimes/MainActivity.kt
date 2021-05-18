package io.ak1.nytimes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import io.ak1.nytimes.utility.getScreenSize
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.setNavigation
import io.ak1.nytimes.utility.setUpStatusNavigationBarColors
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
            ContextCompat.getColor(this, io.ak1.nytimes.R.color.background)
        )
        resources.displayMetrics.getScreenSize()
        setContentView(io.ak1.nytimes.R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(io.ak1.nytimes.R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottom_navigation.setNavigation(navController)
    }
}