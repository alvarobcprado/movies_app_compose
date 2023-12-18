package com.example.moviesapp.data.datasources.local.movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.domain.models.Movie

@Dao
interface MovieCDS {
    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): List<Movie>

    @Query("SELECT * FROM movieDetail WHERE id = :id")
    fun getFavoriteMovie(id: Int): Movie

    @Insert
    fun addFavoriteMovie(vararg movie: Movie)

    @Delete
    fun removeFavoriteMovie(movie: Movie)

}