package moviedleapp.main

import kotlinx.coroutines.*
import moviedleapp.main.helpers.*
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import retrofit2.*


class RepositoryService {
    private val baseUrl = "http://109.207.149.50:8080"

    private val serverApi: ApiInterface = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    suspend fun getAllMovies(): ArrayList<Movie> {
        var allMovies = ArrayList<Movie>()
        CoroutineScope(Dispatchers.IO).launch {
            allMovies = serverApi.getAllMovies().await()
        }.join()
        Logger.logReceivedMovies()
        return allMovies
    }

    suspend fun getRandomMovie(): Movie {
        var movie = Movie()

        CoroutineScope(Dispatchers.IO).launch {
            movie = serverApi.getRandomMovie().await()
        }.join()
        Logger.logRandomMovie(movie.getTitle())
        return movie
    }

    suspend fun getChosenMovieResult(movieTitle: String): MovieWIthComparedAttr {
        var result = MovieWIthComparedAttr()
        CoroutineScope(Dispatchers.IO).launch {
            result = serverApi.guessMovie(movieTitle).await()
        }.join()
        Logger.logReceivedResult()
        return result
    }
}

