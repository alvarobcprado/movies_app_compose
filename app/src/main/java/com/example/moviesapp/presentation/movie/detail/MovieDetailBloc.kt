package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.core.Bloc
import com.ptrbrynt.kotlin_bloc.core.Emitter

class MovieDetailBloc(private val movieRepository: MovieDataRepository) :
    Bloc<MovieDetailEvent, MovieDetailState>(MovieDetailState.Loading) {
    init {
        on<MovieDetailEvent.FetchMovieDetail>(mapEventToState = fetchMovieDetail())
    }

    private fun fetchMovieDetail(): suspend Emitter<MovieDetailState>.(MovieDetailEvent.FetchMovieDetail) -> Unit =
        { event ->
            emit(MovieDetailState.Loading)
            val movieDetailResult = movieRepository.getMovieById(event.id)
            movieDetailResult.fold(
                onSuccess = { emit(MovieDetailState.Success(it)) },
                onFailure = { emit(mapErrorToState(it)) }
            )
        }

    private fun mapErrorToState(it: Throwable): MovieDetailState {
        return MovieDetailState.Error(
            when (it) {
                is NoMoviesException -> MovieDetailErrorType.NOT_FOUND
                is NoInternetException -> MovieDetailErrorType.NETWORK_ERROR
                is ServerException -> MovieDetailErrorType.SERVER_ERROR
                else -> MovieDetailErrorType.UNKNOWN
            }
        )
    }
}