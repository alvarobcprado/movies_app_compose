package com.example.moviesapp.presentation.core.bottomnavbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moviesapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object MovieList : Screen("movie_list", R.string.bottomNavItemMovies, Icons.Filled.Home)
    object FavoriteMovieList : Screen(
        "favorite_movie_list", R.string.bottomNavItemFavorites, Icons.Filled.Favorite
    )
}

val bottomNavItems = listOf(
    Screen.MovieList,
    Screen.FavoriteMovieList
)