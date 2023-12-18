package com.example.moviesapp.presentation.core.routing

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moviesapp.presentation.core.bottomnavbar.Screen
import com.example.moviesapp.presentation.movie.detail.MovieDetailPage
import com.example.moviesapp.presentation.movie.list.MovieListPage

fun NavGraphBuilder.moviesGraph() {
    navigation(
        route = Screen.MovieList.route,
        startDestination = "movies",
    ) {
        composable("movies") {
            MovieListPage()
        }
        composable("movies/{movieId}") { navBackStackEntry ->
            val movieId =
                navBackStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
            MovieDetailPage(movieId = movieId)
        }
    }
}