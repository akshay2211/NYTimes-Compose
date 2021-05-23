package io.ak1.nytimes.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import io.ak1.nytimes.R
import io.ak1.nytimes.utility.isDarkThemeOn
import io.ak1.nytimes.utility.setUpStatusNavigationBarColors
import io.ak1.nytimes.utility.setupTheme

/**
 * [SettingsActivity] container of preference fragment
 * provide lifecycle activity for preference manipulations
 * */
class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setUpStatusNavigationBarColors(
            isDarkThemeOn(),
            ContextCompat.getColor(this, R.color.background)
        )
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commitNow()
        }
        findViewById<ImageView>(R.id.back).setOnClickListener { finish() }
        sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)

    }

    override fun onResume() {
        super.onResume()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key?.equals("list_theme") == true) {
            sharedPreferences?.setupTheme(key, resources)
            window.setUpStatusNavigationBarColors(
                isDarkThemeOn(),
                ContextCompat.getColor(this, R.color.background)
            )
        }

    }

}