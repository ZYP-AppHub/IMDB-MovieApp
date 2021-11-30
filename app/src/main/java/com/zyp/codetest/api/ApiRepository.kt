package com.zyp.codetest.api

import com.zyp.codetest.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUpcomingMovie(page: Int): Response<MovieResponse> {
        return apiService.getUpcomingMovies(page = page)
    }

    suspend fun getPopularMovie(page: Int): Response<MovieResponse> {
        return apiService.getPopularMovies(page = page)
    }

}