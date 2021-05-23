package io.ak1.nytimes.data.remote


import io.ak1.nytimes.model.StoriesResponse
import io.ak1.nytimes.utility.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
interface ApiList {
    //https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=yourkey

    @GET("{section}.json")
    suspend fun getStories(
        @Path("section") section: String = "home",
        @Query("api-key") key: String = API_KEY
    ): Response<StoriesResponse>

    companion object {
        const val BASE_PATH = "https://api.nytimes.com/svc/topstories/v2/"
    }
}
