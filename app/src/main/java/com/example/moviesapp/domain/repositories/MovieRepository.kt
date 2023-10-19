package com.example.moviesapp.domain.repositories

import com.example.moviesapp.domain.models.Movie

interface MovieRepository {
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getMovieById(id: Int): Result<Movie>
}