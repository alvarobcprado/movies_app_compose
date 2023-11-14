package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.core.appModule
import com.example.moviesapp.presentation.movie.detail.MovieDetailPage
import com.example.moviesapp.presentation.movie.list.MovieListPage
import com.example.moviesapp.ui.theme.MoviesAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            App()
        }
    }
}


@Composable
fun App() {
    KoinAndroidContext {
        MoviesAppTheme {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "movies") {
                composable("movies") {
                    MovieListPage(
                        onGoToMovieDetail = { movieId ->
                            navController.navigate("movies/$movieId")
                        },
                    )
                }
                composable("movies/{movieId}") { navBackStackEntry ->
                    val movieId = navBackStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                    MovieDetailPage(movieId = movieId)
                }
            }
        }
    }
}