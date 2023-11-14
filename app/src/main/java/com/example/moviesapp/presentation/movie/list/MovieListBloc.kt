package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.core.BlocViewModel
import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.example.moviesapp.ui.components.pages.MovieErrorType

class MovieListBloc(private val movieRepository: MovieDataRepository) :
    BlocViewModel<MovieListEvent, MovieListState>(MovieListState.Loading) {

    init {
        on<MovieListEvent.FetchMovies> { fetchMovies() }
    }


    private suspend fun fetchMovies() {
        setState(MovieListState.Loading)
        val moviesResult = movieRepository.getMovies()
        moviesResult.fold(
            onSuccess = { setState(MovieListState.Success(it)) },
            onFailure = { setState(mapErrorToState(it)) }
        )
    }

    private fun mapErrorToState(error: Throwable): MovieListState {
        return MovieListState.Error(
            when (error) {
                is NoMoviesException -> MovieErrorType.NOT_FOUND
                is NoInternetException -> MovieErrorType.NETWORK_ERROR
                is ServerException -> MovieErrorType.SERVER_ERROR
                else -> MovieErrorType.UNKNOWN
            }
        )
    }
}
