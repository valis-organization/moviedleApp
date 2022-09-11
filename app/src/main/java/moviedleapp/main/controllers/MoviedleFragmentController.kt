package moviedleapp.main.controllers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moviedleapp.main.Movie
import moviedleapp.main.MoviedleListener
import moviedleapp.main.helpers.EndpointUrl
import moviedleapp.main.helpers.RequestMaker
import okhttp3.OkHttpClient

class MoviedleFragmentController(
   private val moviedleListener: MoviedleListener) {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun initialize() {
        var allMovies = ArrayList<Movie>()
        val job = GlobalScope.launch(Dispatchers.IO) {
            allMovies = getAllMovies()
            for(Movie in allMovies){
                println(Movie.getTitle())
            }
        }
        GlobalScope.launch(Dispatchers.Main) {
            job.join()
            moviedleListener.addMoviesToListView(allMovies)
        }

    }

    private fun getAllMovies(): ArrayList<Movie> {
        val serverResponse = RequestMaker.makeGETRequest(client, EndpointUrl.allMovies())
        val itemType = object : TypeToken<List<Movie>>() {}.type

        return gson.fromJson<List<Movie>>(serverResponse, itemType) as ArrayList<Movie>
    }
}

