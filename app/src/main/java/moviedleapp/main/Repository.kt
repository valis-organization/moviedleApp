package moviedleapp.main

import android.graphics.drawable.Drawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.helpers.Logger
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import java.io.InputStream
import java.net.URL

object Repository {

    private const val baseUrl = "http://109.207.149.50:8080"

    private val serverApi: RepositoryService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RepositoryService::class.java)

    private var allMovies = ArrayList<Movie>()
    private var moviesImage = HashMap<String, Drawable>()

    suspend fun getAllMovies(): ArrayList<Movie> {
        if (allMovies.isEmpty()) {
            val response = serverApi.getAllMovies()

            if (response.isSuccessful) {
                Logger.logReceivedMovies()
                allMovies = response.body()!!
            } else {
                throw Exception("Something went wrong")
            }
            initializeMoviesImage()
        }
        return allMovies
    }

    suspend fun getRandomMovie(): Movie {
        val response = serverApi.getRandomMovie()
        if (response.isSuccessful) {
            val movie = response.body()
            Logger.logRandomMovie(movie!!.getTitle())
            return movie
        } else {
            throw Exception("Something went wrong")
        }
    }

    suspend fun getChosenMovieResult(title: String): MovieWIthComparedAttr {
        val response =  serverApi.guessMovie(title)
        if(response.isSuccessful){
            Logger.logReceivedResult()
            return response.body()!!
        }else{
            throw Exception("Something went wrong.")
        }
    }

    fun getMovieImageByTitle(title: String): Drawable? {
        return moviesImage[title]
    }

    private fun initializeMoviesImage() {
        for (movie in allMovies) {
            CoroutineScope(Dispatchers.IO).launch {
                val image = getDrawableByUrl(movie.getImageUrl())
                withContext(Dispatchers.Default) {
                    moviesImage[movie.getTitle()] = image
                }
            }
        }
    }
    private fun getDrawableByUrl(url: String): Drawable =
        Drawable.createFromStream(URL(url).content as InputStream, "srcName")
}