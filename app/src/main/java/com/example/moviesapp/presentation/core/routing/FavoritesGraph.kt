package com.example.moviesapp.presentation.core.routing

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moviesapp.presentation.core.bottomnavbar.Screen
import com.example.moviesapp.presentation.movie.list.MovieListPage

fun NavGraphBuilder.favoritesGraph() {
    navigation(
        route = Screen.FavoriteMovieList.route,
        startDestination = "favorites",
    ) {
        composable("favorites") {
            MovieListPage()
        }
    }
}