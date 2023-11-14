@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.moviesapp.presentation.movie.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.ui.components.pages.MovieErrorPage
import com.example.moviesapp.ui.components.pages.MovieErrorType
import com.example.moviesapp.ui.components.pages.MovieLoadingPage
import com.example.moviesapp.ui.theme.MoviesAppTheme
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListPage(onGoToMovieDetail: (Int) -> Unit, movieListBloc: MovieListBloc = koinInject()) {
    LaunchedEffect(movieListBloc) {
        movieListBloc.addEvent(MovieListEvent.FetchMovies)
    }

    val state by movieListBloc.state.collectAsState()
    MovieListContent(
        onGoToMovieDetail = onGoToMovieDetail,
        movieListState = state,
        onRetry = {
            movieListBloc.addEvent(MovieListEvent.FetchMovies)
        },
    )

}

@Composable
private fun MovieListTopBar() = TopAppBar(title = { Text(text = "Movies") })

@Composable
private fun MovieListContent(
    onGoToMovieDetail: (Int) -> Unit,
    onRetry: () -> Unit,
    movieListState: MovieListState,
) {
    when (movieListState) {
        is MovieListState.Loading -> MovieLoadingPage()
        is MovieListState.Success -> MovieListSuccess(
            movies = movieListState.movies,
            onMovieClick = onGoToMovieDetail,
        )

        is MovieListState.Error -> MovieErrorPage(
            errorType = movieListState.type,
            onRetry = onRetry
        )
    }
}

@Composable
private fun MovieListSuccess(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {

    Scaffold(topBar = { MovieListTopBar() }) { paddingValues ->
        MovieList(
            movies = movies,
            onMovieClick = onMovieClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieListItem(
                movie = movie,
                onMovieClick = onMovieClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieListItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
) {
    Card(
        onClick = { onMovieClick(movie.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        SubcomposeAsyncImage(
            model = movie.posterUrl, contentDescription = null,
            modifier = Modifier.aspectRatio(128 / 192f),
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .fillMaxSize()
                .wrapContentHeight(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MoviesAppTheme {
        SuccessContentPreview()
    }
}

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
                posterUrl = "https://image.tmdb.org/t/p/w200/2CAL2433ZeIihfX1Hb2139CX0pW.jpg",
            ),
            Movie(
                id = 2,
                title = "Title really long to test overflow ellipsis line",
                releaseDate = "Release Date",
                genres = listOf("Genre 1", "Genre 2"),
                voteAverage = 1.0,
                posterUrl = "https://image.tmdb.org/t/p/w200/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
            ),
            Movie(
                id = 3,
                title = "Title to test overflow ellipsis line",
                releaseDate = "Release Date",
                genres = listOf("Genre 1", "Genre 2"),
                voteAverage = 1.0,
                posterUrl = "https://image.tmdb.org/t/p/w200/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
            ),
        )
    )

    MovieListContent(
        onGoToMovieDetail = {},
        movieListState = movieListState,
        onRetry = {},
    )
}

@Composable
private fun ErrorContentPreview() {
    val movieListState = MovieListState.Error(MovieErrorType.NOT_FOUND)
    MovieListContent(
        onGoToMovieDetail = {},
        movieListState = movieListState,
        onRetry = {},
    )
}