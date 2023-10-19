package com.example.moviesapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("title")
    val title: String,
    @SerialName("poster_u")
    val posterUrl: String,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("release_date")
    val releaseDate: String
)
