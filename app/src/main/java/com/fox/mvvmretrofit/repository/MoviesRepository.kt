package com.fox.mvvmretrofit.repository

import com.fox.mvvmretrofit.api.RetrofitInstance

class MoviesRepository() {
    suspend fun getMoviesR() =
        RetrofitInstance.api.getMovies()
}