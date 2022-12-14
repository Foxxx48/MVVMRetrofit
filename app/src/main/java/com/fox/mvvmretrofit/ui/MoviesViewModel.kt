package com.fox.mvvmretrofit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.mvvmretrofit.models.MovieResponse
import com.fox.mvvmretrofit.repository.MoviesRepository
import com.fox.mvvmretrofit.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(
    val moviesRep: MoviesRepository

) : ViewModel() {

    val moviesPage: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

    init {
        getMoviesVM()
    }

    private fun getMoviesVM() = viewModelScope.launch {
        moviesPage.postValue(Resource.Loading())
        val response = moviesRep.getMoviesR()
        moviesPage.postValue(handleResp(response))
    }

    private fun handleResp(response: Response<MovieResponse>) : Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}