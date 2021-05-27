package io.ak1.nytimes.di

import android.content.Context
import androidx.room.Room
import io.ak1.nytimes.BuildConfig
import io.ak1.nytimes.data.local.AppDatabase
import io.ak1.nytimes.data.remote.ApiList
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext


/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

/**
 * definitions for dependency injection for all selected classes
 */
fun getLogInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

}

fun returnRetrofit(interceptor: HttpLoggingInterceptor): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder().baseUrl(ApiList.BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client).build()
}

fun getApi(retrofit: Retrofit): ApiList {
    return retrofit.create(ApiList::class.java)
}


fun getDb(context: Context): AppDatabase {
    return synchronized(context) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-stories"
        ).build()
    }
}


fun getCoroutineContext(): CoroutineContext {
    return Dispatchers.IO
}
