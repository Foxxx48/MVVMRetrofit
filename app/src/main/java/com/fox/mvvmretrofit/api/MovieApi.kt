package com.fox.mvvmretrofit.api

import com.fox.mvvmretrofit.models.MovieResponse
import com.fox.mvvmretrofit.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("3/list/1")
    suspend fun getMovies(
        @Query("api_key")
        key: String =API_KEY
    ): Response<MovieResponse>
}