package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.domain.models.MovieDetail

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movie: MovieDetail) : MovieDetailState()
    data class Error(val type: MovieDetailErrorType) : MovieDetailState()
}

sealed class MovieDetailEvent {
    data class FetchMovieDetail(val id: Int) : MovieDetailEvent()
}


enum class MovieDetailErrorType {
    NETWORK_ERROR,
    SERVER_ERROR,
    NOT_FOUND,
    UNKNOWN
}