package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.core.BlocViewModel
import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.example.moviesapp.ui.components.pages.MovieErrorType

class MovieDetailBloc(private val movieRepository: MovieDataRepository) :
    BlocViewModel<MovieDetailEvent, MovieDetailState>(MovieDetailState.Loading) {
    init {
        on<MovieDetailEvent.FetchMovieDetail> { event -> fetchMovieDetail(event.id) }
    }

    private suspend fun fetchMovieDetail(movieId: Int) {
        setState(MovieDetailState.Loading)
        val movieDetailResult = movieRepository.getMovieById(movieId)
        movieDetailResult.fold(
            onSuccess = { setState(MovieDetailState.Success(it)) },
            onFailure = { setState(mapErrorToState(it)) }
        )
    }

    private fun mapErrorToState(it: Throwable): MovieDetailState {
        return MovieDetailState.Error(
            when (it) {
                is NoMoviesException -> MovieErrorType.NOT_FOUND
                is NoInternetException -> MovieErrorType.NETWORK_ERROR
                is ServerException -> MovieErrorType.SERVER_ERROR
                else -> MovieErrorType.UNKNOWN
            }
        )
    }
}