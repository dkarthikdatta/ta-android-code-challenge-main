package com.example.mytripactions.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "f30bb00e036844de8eac9c12ed2c6ae5"
    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int = 1
    ): NewsResponse
}
