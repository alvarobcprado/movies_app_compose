package com.example.moviesapp.data.repositories

import com.example.moviesapp.data.datasources.local.movies.MovieCDS
import com.example.moviesapp.data.datasources.remote.movies.MovieRDS
import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.domain.models.MovieDetail
import com.example.moviesapp.domain.repositories.MovieDataRepository

class MovieRepository(private val movieRDS: MovieRDS, private val movieCDS: MovieCDS) :
    MovieDataRepository {

    override suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val movies = movieRDS.getMovieList()
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieById(id: Int): Result<MovieDetail> {
        return try {
            val movie = movieRDS.getMovieDetail(id)
            Result.success(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<MovieDetail>> {
        return try {
            val movies = movieCDS.getFavoriteMovieList()
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoriteMovieById(id: Int): Result<MovieDetail> {
        return try {
            val movie = movieCDS.getFavoriteMovie(id)
            Result.success(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveFavoriteMovie(movie: MovieDetail): Result<Unit> {
        return try {
            movieCDS.addFavoriteMovie(movie)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFavoriteMovie(movie: MovieDetail): Result<Unit> {
        return try {
            movieCDS.removeFavoriteMovie(movie.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}