package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.ui.components.pages.MovieErrorType

sealed class MovieListState {
    object Loading : MovieListState()
    data class Success(val movies: List<Movie>) : MovieListState()
    data class Error(val type: MovieErrorType) : MovieListState()
}

sealed class MovieListEvent {
    object FetchMovies : MovieListEvent()
}


