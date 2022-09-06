package moviedleapp.main.controllers

import android.widget.Button
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.gson.Gson
import moviedleapp.main.Movie
import moviedleapp.main.ServerResponseListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.wait
import java.io.IOException


class HomeFragmentController(
    private val rndMovieButton: Button,
    private val responseListener: ServerResponseListener
) {

    private val client = OkHttpClient()
    private val rndMovieUrl = "http://109.207.149.50:8080/randomMovie"
    private val gson = Gson()

    public fun initialize() {
        var randomMovie : Movie = Movie()
        rndMovieButton.setOnClickListener {
            Thread {
                randomMovie = getRandomMovie()
                responseListener.onReceivingRandomMovie(randomMovie)
            }.start()
        }
    }

    private fun getRandomMovie(): Movie {
        val request = Request.Builder()
            .url(rndMovieUrl)
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseFromServer = response.body!!.string()
            var receivedJson: Movie = gson.fromJson(responseFromServer, Movie::class.java)
            println(receivedJson.title)
            println("button clicked")
            return receivedJson
        }
    }
}