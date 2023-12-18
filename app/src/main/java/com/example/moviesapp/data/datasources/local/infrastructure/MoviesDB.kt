package com.example.moviesapp.data.datasources.local.infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.data.datasources.local.movies.MovieCDS
import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.domain.models.MovieDetail

@Database(entities = [Movie::class, MovieDetail::class], version = 1)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieCDS(): MovieCDS
}