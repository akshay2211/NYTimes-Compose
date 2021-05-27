package io.ak1.nytimes.di

import io.ak1.nytimes.data.repository.StoriesRepository
import io.ak1.nytimes.ui.screens.home.StoriesViewModel
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
    single { StoriesRepository(get(), get(), get(), get()) }
}

var viewModelModule = module {
    viewModel { StoriesViewModel(get()) }
}