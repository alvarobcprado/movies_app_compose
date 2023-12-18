package com.example.moviesapp.data.datasources.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.domain.models.MovieDetail

@Dao
interface MovieCDS {
    @Query("SELECT * FROM movieDetail")
    fun getFavoriteMovieList(): List<MovieDetail>
    
    @Query("SELECT id FROM movieDetail")
    fun getFavoriteMovieIdList(): List<Int>

    @Query("SELECT * FROM movieDetail WHERE id = :id")
    fun getFavoriteMovie(id: Int): MovieDetail

    @Insert
    fun addFavoriteMovie(movieDetail: MovieDetail)

    @Query("DELETE FROM movieDetail WHERE id = :movieId")
    fun removeFavoriteMovie(movieId: Int)


}