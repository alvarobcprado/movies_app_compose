@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.presentation.movie.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.domain.models.MovieDetail
import com.example.moviesapp.ui.components.pages.MovieErrorPage
import com.example.moviesapp.ui.components.pages.MovieLoadingPage
import com.ptrbrynt.kotlin_bloc.compose.BlocComposer
import org.koin.compose.koinInject

@Composable
fun MovieDetailPage(movieId: Int, movieDetailBloc: MovieDetailBloc = koinInject()) {
    LaunchedEffect(movieDetailBloc) {
        movieDetailBloc.add(MovieDetailEvent.FetchMovieDetail(movieId))
    }

    BlocComposer(bloc = movieDetailBloc) { state ->
        MovieDetailContent(
            movieDetailState = state,
            onRetry = { movieDetailBloc.add(MovieDetailEvent.FetchMovieDetail(movieId)) },
        )

    }
}

@Composable
private fun MovieDetailAppBar(movieName: String) {
    TopAppBar(title = { Text(text = movieName) })
}


@Composable
private fun MovieDetailContent(
    movieDetailState: MovieDetailState,
    onRetry: () -> Unit
) {
    when (movieDetailState) {
        is MovieDetailState.Loading -> MovieLoadingPage()
        is MovieDetailState.Success -> MovieDetailSuccess(movieDetail = movieDetailState.movie)
        is MovieDetailState.Error -> MovieErrorPage(
            errorType = movieDetailState.type,
            onRetry = onRetry,
        )
    }
}

@Composable
fun MovieDetailSuccess(movieDetail: MovieDetail) {
    Scaffold(topBar = { MovieDetailAppBar(movieName = movieDetail.title) }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Text(text = movieDetail.title)
        }
    }
}


@Preview
@Composable
fun MovieDetailPagePreview() {
    MovieDetailPageSuccessPreview()
}

@Composable
private fun MovieDetailPageSuccessPreview() {
    MovieDetailSuccess(
        movieDetail = MovieDetail(
            "https://image.tmdb.org/t/p/w500/iNh3BivHyg5sQRPP1KOkzguEX0H.jpg",
            listOf("Title"),
            123,
            "imdbId",
            "English",
            "Original title",
            "https://image.tmdb.org/t/p/w200/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
            "releaseDate",
            "Title",
            8.5,
            1234,
            "overview"
        )
    )
}