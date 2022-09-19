package moviedleapp.main

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import moviedleapp.main.helpers.EndpointUrl
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.ServerApi
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import okhttp3.OkHttpClient


class Repository {
    companion object {

        private val client = OkHttpClient()
        private val gson = Gson()

        fun getAllMovies(): ArrayList<Movie> {
            val serverResponse = ServerApi.makeGETRequest(client, EndpointUrl.allMovies())
            val itemType = object : TypeToken<List<Movie>>() {}.type
            println(itemType)
            return gson.fromJson<List<Movie>>(serverResponse, itemType) as ArrayList<Movie>
        }

        fun getRandomMovie(): Movie {
            val responseFromServer = ServerApi.makeGETRequest(client, EndpointUrl.randomMovieUrl())

            val receivedMovieJson: Movie = gson.fromJson(responseFromServer, Movie::class.java)
            println(receivedMovieJson.getTitle())
            return receivedMovieJson
        }

        fun getChosenMovieResult(movieTitle: String): MovieWIthComparedAttr {
            val responseFromServer =
                ServerApi.makeGETRequest(client, EndpointUrl.guessMovie(movieTitle))

            return gson.fromJson(responseFromServer, MovieWIthComparedAttr::class.java)
        }
    }
}

