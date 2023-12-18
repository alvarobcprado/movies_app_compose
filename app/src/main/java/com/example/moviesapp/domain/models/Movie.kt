package com.example.moviesapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapp.data.datasources.local.infrastructure.StringListConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity
data class Movie(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("title")
    val title: String,
    @SerialName("poster_url")
    val posterUrl: String,
    @SerialName("genres")
    @TypeConverters(StringListConverter::class)
    val genres: List<String>,
    @SerialName("release_date")
    val releaseDate: String
)
