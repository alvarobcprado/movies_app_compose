package com.example.moviesapp.core

import com.example.moviesapp.data.datasources.remote.infrastructure.buildHttpClient
import com.example.moviesapp.data.datasources.remote.movies.MovieRDS
import com.example.moviesapp.data.repositories.MovieRepository
import com.example.moviesapp.domain.repositories.MovieDataRepository
import com.example.moviesapp.presentation.movie.detail.MovieDetailBloc
import com.example.moviesapp.presentation.movie.list.MovieListBloc
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { buildHttpClient(Android.create()) }
    single<MovieRDS> { MovieRDS(get()) }
    single<MovieDataRepository> { MovieRepository(get()) }
    single<MovieListBloc> { MovieListBloc(get()) }
    single<MovieDetailBloc> { MovieDetailBloc(get()) }
}