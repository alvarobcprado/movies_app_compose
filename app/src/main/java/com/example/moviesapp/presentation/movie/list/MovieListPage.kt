@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.moviesapp.presentation.movie.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.ptrbrynt.kotlin_bloc.compose.BlocComposer
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListPage(onGoToMovieDetail: (Int) -> Unit, movieListBloc: MovieListBloc = koinInject()) {
    LaunchedEffect(movieListBloc) {
        movieListBloc.add(MovieListEvent.FetchMovies)
    }

    Scaffold(
        topBar = { MovieListTopBar() },
    )
    { padding ->
        BlocComposer(bloc = movieListBloc) { state ->
            MovieListContent(
                onGoToMovieDetail = onGoToMovieDetail,
                movieListState = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }

}

@Composable
private fun MovieListTopBar() {
    TopAppBar(title = { Text(text = "Movies") })
}

@Composable
private fun MovieListContent(
    onGoToMovieDetail: (Int) -> Unit,
    movieListState: MovieListState,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (movieListState) {
            is MovieListState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is MovieListState.Success -> MovieList(
                movies = movieListState.movies,
                onMovieClick = onGoToMovieDetail,
            )

            is MovieListState.Error -> Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
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
private fun MovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(movies) { movie ->
            MovieListItem(movie = movie, onMovieClick = onMovieClick)
        }
    }
}

@Composable
private fun MovieListItem(movie: Movie, onMovieClick: (Int) -> Unit) {
    Row(modifier = Modifier
        .padding(bottom = 16.dp)
        .clickable { onMovieClick(movie.id) }) {
        Text(text = "Image")
        Spacer(modifier = Modifier.padding(16.dp))
        Column {
            Text(text = movie.title)
            Text(text = movie.releaseDate)
            Text(text = movie.genres.joinToString(", "))
            Text(text = movie.voteAverage.toString())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MoviesAppTheme {
        SuccessContentPreview()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SuccessContentPreview() {
    val movieListState = MovieListState.Success(
        movies = listOf(
            Movie(
                id = 1,
                title = "Title",
                releaseDate = "Release Date",
                genres = listOf("Genre 1", "Genre 2"),
                voteAverage = 1.0,
                posterUrl = "Poster Url",
            ),
            Movie(
                id = 2,
                title = "Title",
                releaseDate = "Release Date",
                genres = listOf("Genre 1", "Genre 2"),
                voteAverage = 1.0,
                posterUrl = "Poster Url",
            ),
        )
    )

    MovieListContent(onGoToMovieDetail = {}, movieListState = movieListState, modifier = Modifier)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ErrorContentPreview() {
    val movieListState = MovieListState.Error(MovieListErrorType.NOT_FOUND)

    MovieListContent(onGoToMovieDetail = {}, movieListState = movieListState, modifier = Modifier)
}