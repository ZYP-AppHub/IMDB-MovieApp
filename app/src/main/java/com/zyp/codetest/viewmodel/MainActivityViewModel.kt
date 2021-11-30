package com.zyp.codetest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyp.codetest.api.ApiRepository
import com.zyp.codetest.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    ViewModel() {

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    val errorMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("tag", "getUpcomingMovies Error: ${throwable.message}")
        _upcomingMovies.postValue(emptyList())
        _popularMovies.postValue(emptyList())
        throwable.message?.let { onError(it) }
        throwable.printStackTrace()
    }

    fun getUpcomingMovies() {

        viewModelScope.launch(exceptionHandler) {
            val response = apiRepository.getUpcomingMovie(1)
            if (response.isSuccessful) {
                _upcomingMovies.postValue(response.body()!!.movies)
            } else {
                Log.d("tag", "getUpcomingMovies Error: ${response.message()}")
                onError(response.message())
            }
        }


    }

    fun getPopularMovies() {
        viewModelScope.launch(exceptionHandler) {
            val response = apiRepository.getPopularMovie(1)
            if (response.isSuccessful) {
                _popularMovies.postValue(response.body()!!.movies)
            } else {
                Log.d("tag", "getPopularMovies Error: ${response.message()}")
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }

}