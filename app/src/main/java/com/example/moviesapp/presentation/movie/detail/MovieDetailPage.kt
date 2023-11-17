@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.presentation.movie.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.moviesapp.core.MoviesAppRoutingPreview
import com.example.moviesapp.domain.models.MovieDetail
import com.example.moviesapp.ui.components.pages.MovieErrorPage
import com.example.moviesapp.ui.components.pages.MovieLoadingPage
import com.example.moviesapp.ui.components.widgets.MoviesTopBar
import com.example.moviesapp.ui.theme.MoviesAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailPage(
    movieId: Int,
    movieDetailBloc: MovieDetailBloc = koinViewModel(),
) {
    LaunchedEffect(movieDetailBloc) {
        movieDetailBloc.addEvent(MovieDetailEvent.FetchMovieDetail(movieId))
    }

    val state by movieDetailBloc.state.collectAsState()
    MovieDetailContent(
        movieDetailState = state,
        onRetry = { movieDetailBloc.addEvent(MovieDetailEvent.FetchMovieDetail(movieId)) },
    )
}

@Composable
private fun MovieDetailContent(
    movieDetailState: MovieDetailState,
    onRetry: () -> Unit,
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
private fun MovieDetailSuccess(movieDetail: MovieDetail) {
    Scaffold(topBar = { MoviesTopBar(title = movieDetail.title) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                SubcomposeAsyncImage(
                    model = movieDetail.posterUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .heightIn(min = 180.dp, max = 200.dp)
                        .aspectRatio(2 / 3.5f),
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                MovieDetailInfoColumn(movieDetail = movieDetail)
            }
        }
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "Overview",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = movieDetail.overview, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun MovieDetailInfoLine(
    title: String,
    value: String,
) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun MovieDetailInfoColumn(movieDetail: MovieDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MovieDetailInfoLine(
            title = "Title",
            value = movieDetail.title,
        )
        MovieDetailInfoLine(
            title = "Original title",
            value = movieDetail.originalTitle,
        )
        MovieDetailInfoLine(
            title = "Release date",
            value = movieDetail.releaseDate,
        )
        MovieDetailInfoLine(
            title = "Language",
            value = movieDetail.originalLanguage,
        )
        MovieDetailInfoLine(
            title = "Genres", value = movieDetail.genres.joinToString(", ")
        )
        MovieDetailInfoLine(
            title = "Rating",
            value = movieDetail.voteAverage.toString(),
        )
        MovieDetailInfoLine(
            title = "Vote count",
            value = movieDetail.voteCount.toString(),
        )
    }
}


@Preview
@Composable
fun MovieDetailPagePreview() {
    MoviesAppTheme {
        MoviesAppRoutingPreview {
            MovieDetailPageSuccessPreview()
        }
    }
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