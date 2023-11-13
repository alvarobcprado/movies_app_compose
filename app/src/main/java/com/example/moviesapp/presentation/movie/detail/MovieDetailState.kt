package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.domain.models.MovieDetail
import com.example.moviesapp.ui.components.pages.MovieErrorType

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movie: MovieDetail) : MovieDetailState()
    data class Error(val type: MovieErrorType) : MovieDetailState()
}

sealed class MovieDetailEvent {
    data class FetchMovieDetail(val id: Int) : MovieDetailEvent()
}
