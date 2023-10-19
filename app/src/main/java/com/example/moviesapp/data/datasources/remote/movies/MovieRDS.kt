package com.example.moviesapp.data.datasources.remote.movies

import com.example.moviesapp.domain.models.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieRDS(val client: HttpClient) {
    private val BASE_URL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/"
    private val MOVIE_LIST = "movies-v2"

    suspend fun getMovieList(): List<Movie> {
        return client.get(BASE_URL + MOVIE_LIST).body()
    }

    suspend fun getMovieDetail(id: Int): Movie {
        return client.get("$BASE_URL$MOVIE_LIST/$id").body()
    }

}