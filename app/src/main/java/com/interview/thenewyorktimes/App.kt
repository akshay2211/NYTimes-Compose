package com.interview.thenewyorktimes

import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.interview.thenewyorktimes.di.databaseModule
import com.interview.thenewyorktimes.di.networkModule
import com.interview.thenewyorktimes.di.repoModule
import com.interview.thenewyorktimes.di.viewModelModule
import com.interview.thenewyorktimes.utility.setupTheme
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