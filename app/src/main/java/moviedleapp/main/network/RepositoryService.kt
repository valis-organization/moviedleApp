package moviedleapp.main.network

import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RepositoryService {

    @GET("/moviesList")
    suspend fun getAllMovies(): Response<ArrayList<Movie>>

    @GET("/randomMovie")
    suspend fun getRandomMovie(): Response<Movie>

    @POST("/games/classic/guess")
    suspend fun guessMovie(@Body movieTitle: String): Response<MovieWIthComparedAttr>

    @POST("/tokenSignIn")
    suspend fun loginByToken(@Body tokenId :String) : Response<Unit>
}