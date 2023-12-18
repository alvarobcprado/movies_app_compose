package com.example.moviesapp.presentation.core.routing

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.presentation.core.bottomnavbar.Screen
import com.example.moviesapp.presentation.core.bottomnavbar.bottomNavItems

val LocalNavController = compositionLocalOf<NavController> {
    error("No nav controller provided")
}

@Composable
fun MoviesAppRouting(navController: NavHostController) {
    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            bottomBar = {
                MoviesAppBottomBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.MovieList.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                moviesGraph()
                favoritesGraph()
            }
        }
    }
}

@Composable
private fun MoviesAppBottomBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = stringResource(screen.resourceId)
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
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