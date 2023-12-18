package com.example.moviesapp.data.datasources.local.movies

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviesapp.data.datasources.local.infrastructure.MoviesDB
import com.example.moviesapp.domain.models.MovieDetail
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieCDSTest {
    companion object {
        private val favoriteMovie = MovieDetail(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterUrl = "Poster Path 1",
            backdropImageUrl = "Backdrop Path 1",
            voteAverage = 1.0,
            releaseDate = "Release Date 1",
            genres = listOf("Genre 1"),
            imdbId = "IMDB ID 1",
            originalLanguage = "Original Language 1",
            originalTitle = "Original Title 1",
            voteCount = 1,
        )
    }

    private lateinit var movieCDS: MovieCDS
    private lateinit var movieDB: MoviesDB

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        movieDB = Room.inMemoryDatabaseBuilder(context, MoviesDB::class.java).build()
        movieCDS = movieDB.movieCDS()
    }

    @AfterEach
    @Throws(Exception::class)
    fun tearDown() {
        movieDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun getFavoriteMovieList() {
        val movie = favoriteMovie
        movieCDS.addFavoriteMovie(movie)
        val favoriteMovieList = movieCDS.getFavoriteMovieList()
        assert(favoriteMovieList.isNotEmpty())
        assert(favoriteMovieList.contains(movie))
    }


}