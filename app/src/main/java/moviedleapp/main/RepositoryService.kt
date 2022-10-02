package moviedleapp.main

import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {

    @GET("/moviesList")
    suspend fun getAllMovies(): Response<ArrayList<Movie>>

    @GET("/randomMovie")
    suspend fun getRandomMovie(): Response<Movie>

    @GET("/games/classic/guess/{movieTitle}")
    suspend fun guessMovie(@Path("movieTitle") movieTitle: String): Response<MovieWIthComparedAttr>
}