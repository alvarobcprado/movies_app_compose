package com.example.moviesapp.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.presentation.movie.detail.MovieDetailPage
import com.example.moviesapp.presentation.movie.list.MovieListPage

val LocalNavController = compositionLocalOf<NavController> {
    error("No nav controller provided")
}

@Composable
fun MoviesAppRouting(navController: NavHostController) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "movies") {
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
}

@Composable
fun MoviesAppRoutingPreview(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController(),
        content = content,
    )
}

fun NavController.canGoBack(): Boolean {
    return previousBackStackEntry != null
}

fun NavController.goToMovieDetail(movieId: Int) {
    navigate("movies/$movieId")
}