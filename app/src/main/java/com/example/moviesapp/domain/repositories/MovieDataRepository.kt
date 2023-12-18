package com.example.moviesapp.domain.repositories

import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.domain.models.MovieDetail

interface MovieDataRepository {
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getMovieById(id: Int): Result<MovieDetail>

    suspend fun getFavoriteMovies(): Result<List<MovieDetail>>

    suspend fun getFavoriteMovieById(id: Int): Result<MovieDetail>

    suspend fun saveFavoriteMovie(movie: MovieDetail): Result<Unit>

    suspend fun deleteFavoriteMovie(movie: MovieDetail): Result<Unit>
}