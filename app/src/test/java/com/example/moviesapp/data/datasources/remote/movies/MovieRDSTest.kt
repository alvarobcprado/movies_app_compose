package com.example.moviesapp.data.datasources.remote.movies

import com.example.moviesapp.data.datasources.remote.infrastructure.buildHttpClient
import com.example.moviesapp.domain.models.Movie
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MovieRDSTest {
    companion object {
        const val mockList =
            "[{\"id\":19404,\"vote_average\":9.3,\"title\":\"DilwaleDulhaniaLeJayenge\",\"poster_url\":\"https://image.tmdb.org/t/p/w200/2CAL2433ZeIihfX1Hb2139CX0pW.jpg\",\"genres\":[\"Comedy\",\"Drama\",\"Romance\"],\"release_date\":\"1995-10-20\"},{\"id\":278,\"vote_average\":8.6,\"title\":\"TheShawshankRedemption\",\"poster_url\":\"https://image.tmdb.org/t/p/w200/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg\",\"genres\":[\"Drama\",\"Crime\"],\"release_date\":\"1994-09-23\"}]"
        const val mockDetail =
            "{\"adult\":false,\"backdrop_url\":\"https://image.tmdb.org/t/p/w500/poec6RqOKY9iSiIUmfyfPfiLtvBx.jpg\",\"belongs_to_collection\":{\"id\":230,\"name\":\"TheGodfatherCollection\",\"poster_url\":\"https://image.tmdb.org/t/p/w200/9Baumh5J9N1nJUYzNkm0xsgjpwY.jpg\",\"backdrop_url\":\"https://image.tmdb.org/t/p/w500/3WZTxpgscsmoUk81TuECXdFOD0R.jpg\"},\"budget\":13000000,\"genres\":[\"Drama\",\"Crime\"],\"homepage\":null,\"id\":240,\"imdb_id\":\"tt0071562\",\"original_language\":\"en\",\"original_title\":\"TheGodfather:PartII\",\"overview\":\"InthecontinuingsagaoftheCorleonecrimefamily,ayoungVitoCorleonegrowsupinSicilyandin1910sNewYork.Inthe1950s,MichaelCorleoneattemptstoexpandthefamilybusinessintoLasVegas,HollywoodandCuba.\",\"popularity\":17.578,\"poster_url\":\"https://image.tmdb.org/t/p/w200/v3KCBeX0CBlZnHZndimm7taYqwo.jpg\",\"production_companies\":[{\"id\":4,\"logo_url\":\"https://image.tmdb.org/t/p/w200/fycMZt242LVjagMByZOLUGbCvv3.png\",\"name\":\"Paramount\",\"origin_country\":\"US\"},{\"id\":536,\"logo_url\":null,\"name\":\"TheCoppolaCompany\",\"origin_country\":\"\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"UnitedStatesofAmerica\"}],\"release_date\":\"1974-12-20\",\"revenue\":102600000,\"runtime\":200,\"spoken_languages\":[{\"iso_639_1\":\"en\",\"name\":\"English\"},{\"iso_639_1\":\"it\",\"name\":\"Italiano\"},{\"iso_639_1\":\"la\",\"name\":\"Latin\"},{\"iso_639_1\":\"es\",\"name\":\"Español\"}],\"status\":\"Released\",\"tagline\":\"Idon'tfeelIhavetowipeeverybodyout,Tom.Justmyenemies.\",\"title\":\"TheGodfather:PartII\",\"video\":false,\"vote_average\":8.5,\"vote_count\":4794}"
    }

    private fun mockClient(response: String): HttpClient {
        val engine = MockEngine {
            respond(
                content = ByteReadChannel(response),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return buildHttpClient(engine)
    }


    @Test
    fun `SHOULD get the list of Movies`() = runBlocking<Unit> {
        val client = mockClient(mockList)
        val movieRDS = MovieRDS(client)
        val movieList: List<Movie> = movieRDS.getMovieList()

        Assertions.assertEquals(2, movieList.size)
        Assertions.assertEquals(19404, movieList.first().id)
        Assertions.assertEquals(278, movieList.last().id)
    }


    @Test
    fun `GIVEN the movie id THEN should get the MovieDetail`() = runBlocking {
        val client = mockClient(mockDetail)
        val movieRDS = MovieRDS(client)
        val movieDetail = movieRDS.getMovieDetail(240)

        Assertions.assertEquals(240, movieDetail.id)
        Assertions.assertEquals("tt0071562", movieDetail.imdbId)
        Assertions.assertEquals(8.5, movieDetail.voteAverage)
        Assertions.assertEquals(4794, movieDetail.voteCount)
    }
}