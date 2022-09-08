package moviedleapp.main.controllers

import android.widget.Button
import com.google.gson.Gson
import kotlinx.coroutines.*
import moviedleapp.main.Movie
import moviedleapp.main.ServerResponseListener
import moviedleapp.main.helpers.EndpointUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class HomeFragmentController(
    private val rndMovieButton: Button,
    private val responseListener: ServerResponseListener
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun initialize() {
        var randomMovie= Movie()
        rndMovieButton.setOnClickListener {
            val job = GlobalScope.launch(Dispatchers.IO) { randomMovie = getRandomMovie() }
            GlobalScope.launch(Dispatchers.Main) {
                job.join()
                responseListener.onReceivingRandomMovie(randomMovie)
            }
        }
    }

    private fun getRandomMovie(): Movie {
        val request = Request.Builder()
            .url(EndpointUrl.randomMovieUrl())
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseFromServer = response.body!!.string()
            val receivedMovieJson: Movie = gson.fromJson(responseFromServer, Movie::class.java)
            println(receivedMovieJson.getTitle())
            println("Random movie button clicked.")
            return receivedMovieJson
        }

    }
}