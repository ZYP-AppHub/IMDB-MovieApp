package com.zyp.codetest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zyp.codetest.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Insert
    suspend fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getById(id: Long): LiveData<Movie>

    @Query("SELECT * FROM Movie WHERE movieType = :type")
    fun getByType(type: String): LiveData<List<Movie>>

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}