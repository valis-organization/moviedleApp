package moviedleapp.main.helpers

import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/moviesList")
    fun getAllMovies(): Call<ArrayList<Movie>>

    @GET("/randomMovie")
    fun getRandomMovie(): Call<Movie>

    @GET("/games/classic/guess/{movieTitle}")
    fun guessMovie(@Path("movieTitle") movieTitle: String) : Call<MovieWIthComparedAttr>
}