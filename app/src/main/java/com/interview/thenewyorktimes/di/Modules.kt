package com.interview.thenewyorktimes.di

import androidx.lifecycle.SavedStateHandle
import com.interview.thenewyorktimes.data.repository.StoriesRepository
import com.interview.thenewyorktimes.ui.home.HomeViewModel
import com.interview.thenewyorktimes.ui.home.StoriesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */


/**
 * modules for dependency injection where [single] represents singleton class
 */

var networkModule = module {
    single { getLogInterceptor() }
    single { returnRetrofit(get()) }
    single { getApi(get()) }
}

var databaseModule = module {
    single { getDb(androidApplication()) }
}

var repoModule = module {
    single { getCoroutineContext() }
    single { getGlide(get()) }
    single { StoriesRepository(get(), get(), get(), get()) }
}

var viewModelModule = module {
    viewModel { StoriesViewModel(get()) }
    viewModel { (state: SavedStateHandle) -> HomeViewModel(handle = state, get()) }
}