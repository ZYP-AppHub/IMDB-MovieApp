package com.zyp.codetest.database

import androidx.lifecycle.LiveData
import com.zyp.codetest.model.Movie
import javax.inject.Inject

class DBRepository @Inject constructor(private val appDatabase: AppDatabase) {

    suspend fun insertMovie(movie: Movie): Long {
        return appDatabase.movieDao().insert(movie)
    }

    suspend fun insertMovie(movies: List<Movie>) {
        return appDatabase.movieDao().insert(movies)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return appDatabase.movieDao().getAll()
    }

    fun getMovieById(id: Long): LiveData<Movie> {
        return appDatabase.movieDao().getById(id)
    }

    fun getMovieByType(type: String): LiveData<List<Movie>> {
        return appDatabase.movieDao().getByType(type)
    }

    suspend fun updateMovie(movie: Movie) {
        appDatabase.movieDao().update(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        appDatabase.movieDao().delete(movie)
    }

//    suspend fun deleteAll() {
//        appDatabase.movieDao().deleteAll()
//    }


}