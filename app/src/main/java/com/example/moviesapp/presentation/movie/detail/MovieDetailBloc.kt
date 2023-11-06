package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.core.Bloc
import com.ptrbrynt.kotlin_bloc.core.Emitter

class MovieDetailBloc(private val movieRepository: MovieDataRepository) :
    Bloc<MovieDetailEvent, MovieDetailState>(Loading) {
    init {
        on<FetchMovieDetail>(mapEventToState = fetchMovieDetail())
    }

    private fun fetchMovieDetail(): suspend Emitter<MovieDetailState>.(FetchMovieDetail) -> Unit =
        { event ->
            emit(Loading)
            val movieDetailResult = movieRepository.getMovieById(event.id)
            movieDetailResult.fold(
                onSuccess = { emit(Success(it)) },
                onFailure = { emit(mapErrorToState(it)) }
            )
        }

    private fun mapErrorToState(it: Throwable): MovieDetailState {
        return when (it) {
            is NoMoviesException -> Error(MovieDetailErrorType.NOT_FOUND)
            is NoInternetException -> Error(MovieDetailErrorType.NETWORK_ERROR)
            is ServerException -> Error(MovieDetailErrorType.SERVER_ERROR)
            else -> Error(MovieDetailErrorType.UNKNOWN)
        }
    }
}