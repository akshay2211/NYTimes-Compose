package io.ak1.nytimes

import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import io.ak1.nytimes.di.databaseModule
import io.ak1.nytimes.di.networkModule
import io.ak1.nytimes.di.repoModule
import io.ak1.nytimes.di.viewModelModule
import io.ak1.nytimes.utility.setupTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(applicationContext)
            // use Android logger - Level.INFO by default
            // androidLogger(Level.DEBUG)
            koin.loadModules(listOf(databaseModule, networkModule, viewModelModule, repoModule))
            koin.createRootScope()
        }
        PreferenceManager.getDefaultSharedPreferences(this).setupTheme("list_theme", resources)
    }
}