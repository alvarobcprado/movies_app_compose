@file:OptIn(ExperimentalTime::class)

package com.example.moviesapp.presentation.movie.detail

import com.example.moviesapp.domain.NoInternetException
import com.example.moviesapp.domain.NoMoviesException
import com.example.moviesapp.domain.ServerException
import com.example.moviesapp.domain.models.MovieDetail
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.ptrbrynt.kotlin_bloc.test.testBloc
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

class MovieDetailBlocTest {
    private val movieRepository = mockk<MovieDataRepository>()

    companion object {
        private val movieDetail = MovieDetail(
            id = 1,
            title = "Title",
            overview = "Overview",
            releaseDate = "ReleaseDate",
            voteAverage = 1.0,
            voteCount = 1,
            backdropImageUrl = "BackdropImageUrl",
            genres = emptyList(),
            imdbId = "ImdbId",
            originalLanguage = "OriginalLanguage",
            originalTitle = "OriginalTitle",
            posterUrl = "PosterUrl",
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun fetchMovieDetailShouldEmitCorrectStatesWhenSuccess() {
        runBlocking {
            testBloc(
                setUp = {
                    coEvery { movieRepository.getMovieById(any()) } returns Result.success(
                        movieDetail
                    )
                },
                build = { MovieDetailBloc(movieRepository) },
                act = { add(MovieDetailEvent.FetchMovieDetail(1)) },
                expected = listOf(
                    { equals(MovieDetailState.Loading) },
                    { equals(MovieDetailState.Success(movieDetail)) }
                )
            )
        }
    }

    @Test
    fun fetchMovieDetailShouldEmitCorrectStatesWhenNoMoviesException() {
        runBlocking {
            testBloc(
                setUp = {
                    coEvery { movieRepository.getMovieById(any()) } returns Result.failure(
                        NoMoviesException("No movies found")
                    )
                },
                build = { MovieDetailBloc(movieRepository) },
                act = { add(MovieDetailEvent.FetchMovieDetail(1)) },
                expected = listOf(
                    { equals(MovieDetailState.Loading) },
                    { equals(MovieDetailState.Error(MovieDetailErrorType.NOT_FOUND)) }
                )
            )
        }
    }

    @Test
    fun fetchMovieDetailShouldEmitCorrectStatesWhenNoInternetException() {
        runBlocking {
            testBloc(
                setUp = {
                    coEvery { movieRepository.getMovieById(any()) } returns Result.failure(
                        NoInternetException("No internet")
                    )
                },
                build = { MovieDetailBloc(movieRepository) },
                act = { add(MovieDetailEvent.FetchMovieDetail(1)) },
                expected = listOf(
                    { equals(MovieDetailState.Loading) },
                    { equals(MovieDetailState.Error(MovieDetailErrorType.NETWORK_ERROR)) }
                )
            )
        }
    }

    @Test
    fun fetchMovieDetailShouldEmitCorrectStatesWhenServerException() {
        runBlocking {
            testBloc(
                setUp = {
                    coEvery { movieRepository.getMovieById(any()) } returns Result.failure(
                        ServerException(
                            "Server error"
                        )
                    )
                },
                build = { MovieDetailBloc(movieRepository) },
                act = { add(MovieDetailEvent.FetchMovieDetail(1)) },
                expected = listOf(
                    { equals(MovieDetailState.Loading) },
                    { equals(MovieDetailState.Error(MovieDetailErrorType.SERVER_ERROR)) }
                )
            )
        }
    }

    @Test
    fun fetchMovieDetailShouldEmitCorrectStatesWhenUnknownException() {
        runBlocking {
            testBloc(
                setUp = {
                    coEvery { movieRepository.getMovieById(any()) } returns Result.failure(
                        Exception("Unknown error")
                    )
                },
                build = { MovieDetailBloc(movieRepository) },
                act = { add(MovieDetailEvent.FetchMovieDetail(1)) },
                expected = listOf(
                    { equals(MovieDetailState.Loading) },
                    { equals(MovieDetailState.Error(MovieDetailErrorType.UNKNOWN)) }
                )
            )
        }
    }

}