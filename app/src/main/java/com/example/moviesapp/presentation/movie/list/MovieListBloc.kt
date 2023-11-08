package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.core.Bloc
import com.ptrbrynt.kotlin_bloc.core.Emitter

class MovieListBloc(private val movieRepository: MovieDataRepository) :
    Bloc<MovieListEvent, MovieListState>(MovieListState.Loading) {
    init {
        on<MovieListEvent.FetchMovies>(mapEventToState = fetchMovies())
    }

    private fun fetchMovies(): suspend Emitter<MovieListState>.(MovieListEvent.FetchMovies) -> Unit =
        {
            emit(MovieListState.Loading)
            val moviesResult = movieRepository.getMovies()
            moviesResult.fold(
                onSuccess = { emit(MovieListState.Success(it)) },
                onFailure = { emit(mapErrorToState(it)) }
            )
        }

    private fun mapErrorToState(error: Throwable): MovieListState {
        return MovieListState.Error(
            when (error) {
                is NoMoviesException -> MovieListErrorType.NOT_FOUND
                is NoInternetException -> MovieListErrorType.NETWORK_ERROR
                is ServerException -> MovieListErrorType.SERVER_ERROR
                else -> MovieListErrorType.UNKNOWN
            }
        )
    }
}
