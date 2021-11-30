package com.zyp.codetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyp.codetest.database.DBRepository
import com.zyp.codetest.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val dbRepository: DBRepository) :
    ViewModel() {

    fun insertFavorite(movie: Movie) {
        viewModelScope.launch {
            dbRepository.insertMovie(movie)
        }
    }

    fun getMovieById(movie: Movie): LiveData<Movie> {
        return dbRepository.getMovieById(movie.id)
    }

    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch {
            dbRepository.deleteMovie(movie)
        }
    }

}