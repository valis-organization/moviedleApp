package moviedleapp.main.controllers

import android.widget.Button
import com.google.gson.Gson
import kotlinx.coroutines.*
import moviedleapp.main.Movie
import moviedleapp.main.ServerResponseListener
import moviedleapp.main.helpers.EndpointUrl
import moviedleapp.main.helpers.RequestMaker
import okhttp3.OkHttpClient


class HomeFragmentController(
    private val rndMovieButton: Button,
    private val responseListener: ServerResponseListener
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun initialize() {
        var randomMovie = Movie()
        rndMovieButton.setOnClickListener {
            val job = GlobalScope.launch(Dispatchers.IO) { randomMovie = getRandomMovie() }
            GlobalScope.launch(Dispatchers.Main) {
                job.join()
                responseListener.onReceivingRandomMovie(randomMovie)
            }
        }
    }

    private fun getRandomMovie(): Movie {
        val responseFromServer = RequestMaker.makeGETRequest(client, EndpointUrl.randomMovieUrl())

        val receivedMovieJson: Movie = gson.fromJson(responseFromServer, Movie::class.java)
        println(receivedMovieJson.getTitle())
        return receivedMovieJson
    }
}
