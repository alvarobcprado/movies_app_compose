package com.example.moviesapp.core

import androidx.room.Room
import com.example.moviesapp.data.datasources.local.infrastructure.MoviesDB
import com.example.moviesapp.data.datasources.local.movies.MovieCDS
import com.example.moviesapp.data.datasources.remote.infrastructure.buildHttpClient
import com.example.moviesapp.data.datasources.remote.movies.MovieRDS
import com.example.moviesapp.data.repositories.MovieRepository
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.example.moviesapp.presentation.movie.detail.MovieDetailBloc
import com.example.moviesapp.presentation.movie.list.MovieListBloc
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NAME = "movies.db"

val appModule = module {
    single<HttpClient> { buildHttpClient(Android.create()) }
    single<MoviesDB> {
        Room.databaseBuilder(
            androidApplication(),
            MoviesDB::class.java,
            DATABASE_NAME
        ).build()
    }
    single<MovieCDS> { get<MoviesDB>().movieCDS() }
    single<MovieRDS> { MovieRDS(get()) }
    single<MovieDataRepository> { MovieRepository(get()) }
    viewModel<MovieListBloc> { MovieListBloc(get()) }
    viewModel<MovieDetailBloc> { MovieDetailBloc(get()) }
}