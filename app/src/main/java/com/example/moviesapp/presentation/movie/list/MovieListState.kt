package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.domain.models.Movie

sealed class MovieListState
object Loading : MovieListState()
data class Success(val movies: List<Movie>) : MovieListState()
data class Error(val type: MovieListErrorType) : MovieListState()

sealed class MovieListEvent
object FetchMovies : MovieListEvent()


enum class MovieListErrorType {
    NETWORK_ERROR,
    SERVER_ERROR,
    NOT_FOUND,
    UNKNOWN
}