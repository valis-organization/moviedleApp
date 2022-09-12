package moviedleapp.main

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import moviedleapp.main.helpers.EndpointUrl
import moviedleapp.main.helpers.RequestMaker
import okhttp3.OkHttpClient

class Repository {
    companion object{

        private val client = OkHttpClient()
        private val gson = Gson()

        fun getAllMovies(): ArrayList<Movie> {
            val serverResponse = RequestMaker.makeGETRequest(client, EndpointUrl.allMovies())
            val itemType = object : TypeToken<List<Movie>>() {}.type

            return gson.fromJson<List<Movie>>(serverResponse, itemType) as ArrayList<Movie>
        }

        fun getRandomMovie(): Movie {
            val responseFromServer = RequestMaker.makeGETRequest(client, EndpointUrl.randomMovieUrl())

            val receivedMovieJson: Movie = gson.fromJson(responseFromServer, Movie::class.java)
            println(receivedMovieJson.getTitle())
            return receivedMovieJson
        }
    }
}