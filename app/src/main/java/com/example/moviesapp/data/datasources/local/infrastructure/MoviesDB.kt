package com.example.moviesapp.data.datasources.local.infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesapp.data.datasources.local.movies.MovieCDS
import com.example.moviesapp.domain.models.MovieDetail

@Database(entities = [MovieDetail::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieCDS(): MovieCDS
}