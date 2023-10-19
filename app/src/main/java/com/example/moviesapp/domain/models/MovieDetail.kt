package com.example.moviesapp.domain.models

data class MovieDetail(
    val backdropImageUrl: String,
    val genres: List<String>,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
)
