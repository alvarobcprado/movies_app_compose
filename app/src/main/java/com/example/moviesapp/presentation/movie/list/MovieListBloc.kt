package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.core.Bloc
import com.ptrbrynt.kotlin_bloc.core.Emitter

class MovieListBloc(private val movieRepository: MovieDataRepository) :
    Bloc<MovieListEvent, MovieListState>(Loading) {
    init {
        on<FetchMovies>(mapEventToState = fetchMovies())
    }

    private fun fetchMovies(): suspend Emitter<MovieListState>.(FetchMovies) -> Unit = {
        emit(Loading)
        val moviesResult = movieRepository.getMovies()
        moviesResult.fold(
            onSuccess = { emit(Success(it)) },
            onFailure = { emit(mapErrorToState(it)) }
        )
    }

    private fun mapErrorToState(error: Throwable): MovieListState {
        return when (error) {
            is NoMoviesException -> Error(MovieListErrorType.NOT_FOUND)
            is NoInternetException -> Error(MovieListErrorType.NETWORK_ERROR)
            is ServerException -> Error(MovieListErrorType.SERVER_ERROR)
            else -> Error(MovieListErrorType.UNKNOWN)
        }
    }
}
