@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.presentation.movie.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapp.domain.models.Movie
import com.ptrbrynt.kotlin_bloc.compose.BlocComposer
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListPage(onGoToMovieDetail: (Int) -> Unit, movieListBloc: MovieListBloc = koinInject()) {
    LaunchedEffect(movieListBloc) {
        movieListBloc.add(FetchMovies)
    }

    Scaffold(
        topBar = { MovieListTopBar() },
    )
    { padding ->
        BlocComposer(bloc = movieListBloc) { state ->
            MovieListContent(
                onGoToMovieDetail = onGoToMovieDetail,
                movieListState = state,
                modifier = Modifier.padding(padding)
            )
        }
    }

}

@Composable
fun MovieListTopBar() {
    TopAppBar(title = { Text(text = "Movies") })
}

@Composable
fun MovieListContent(
    onGoToMovieDetail: (Int) -> Unit,
    movieListState: MovieListState,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (movieListState) {
            is Loading -> Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is Success -> MovieList(
                movies = movieListState.movies,
                onMovieClick = onGoToMovieDetail,
                modifier = modifier
            )

            is Error -> Box(modifier = modifier, contentAlignment = Alignment.Center) {
                val text = when (movieListState.type) {
                    MovieListErrorType.NETWORK_ERROR -> "Network Error"
                    MovieListErrorType.SERVER_ERROR -> "Server Error"
                    MovieListErrorType.NOT_FOUND -> "Not Found"
                    MovieListErrorType.UNKNOWN -> "Unknown Error"
                }
                Text(text = text, modifier = modifier)
            }
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(movies) { movie ->
            MovieListItem(movie = movie, onMovieClick = onMovieClick, modifier = modifier)
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onMovieClick: (Int) -> Unit, modifier: Modifier) {
    Row(modifier = modifier.clickable { onMovieClick(movie.id) }) {
        Text(text = "Image")
        Column {
            Text(text = movie.title)
            Text(text = movie.releaseDate)
            Text(text = movie.genres.joinToString(", "))
            Text(text = movie.voteAverage.toString())
        }
    }
}
