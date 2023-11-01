@file:OptIn(ExperimentalTime::class, ExperimentalTime::class)

package com.example.moviesapp.presentation.movie.list

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.test.testBloc
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieListBlocTest {
    private val movieRepository = mockk<MovieDataRepository>()

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun fetchMoviesShouldEmitCorrectStatesWhenSuccess() = runBlocking {
        testBloc(
            setUp = { coEvery { movieRepository.getMovies() } returns Result.success(emptyList()) },
            build = { MovieListBloc(movieRepository) },
            act = { add(FetchMovies) },
            expected = listOf(
                { equals(Loading) },
                { equals(Success(emptyList())) }
            )
        )
    }

    @Test
    fun fetchMoviesShouldEmitCorrectStatesWhenNoMoviesException() = runBlocking {
        testBloc(
            setUp = {
                coEvery { movieRepository.getMovies() } returns Result.failure(
                    NoMoviesException("No movies found")
                )
            },
            build = { MovieListBloc(movieRepository) },
            act = { add(FetchMovies) },
            expected = listOf(
                { equals(Loading) },
                { equals(Error(MovieListErrorType.NOT_FOUND)) }
            )
        )
    }

    @Test
    fun fetchMoviesShouldEmitCorrectStatesWhenNoInternetException() = runBlocking {
        testBloc(
            setUp = {
                coEvery { movieRepository.getMovies() } returns Result.failure(
                    NoInternetException("No internet")
                )
            },
            build = { MovieListBloc(movieRepository) },
            act = { add(FetchMovies) },
            expected = listOf(
                { equals(Loading) },
                { equals(Error(MovieListErrorType.NETWORK_ERROR)) }
            )
        )
    }

    @Test
    fun fetchMoviesShouldEmitCorrectStatesWhenServerException() = runBlocking {
        testBloc(
            setUp = {
                coEvery { movieRepository.getMovies() } returns Result.failure(
                    ServerException(
                        "Server error"
                    )
                )
            },
            build = { MovieListBloc(movieRepository) },
            act = { add(FetchMovies) },
            expected = listOf(
                { equals(Loading) },
                { equals(Error(MovieListErrorType.SERVER_ERROR)) }
            )
        )
    }

    @Test
    fun fetchMoviesShouldEmitCorrectStatesWhenUnknownException() = runBlocking {
        testBloc(
            setUp = { coEvery { movieRepository.getMovies() } returns Result.failure(Exception("Unknown error")) },
            build = { MovieListBloc(movieRepository) },
            act = { add(FetchMovies) },
            expected = listOf(
                { equals(Loading) },
                { equals(Error(MovieListErrorType.UNKNOWN)) }
            )
        )
    }
}