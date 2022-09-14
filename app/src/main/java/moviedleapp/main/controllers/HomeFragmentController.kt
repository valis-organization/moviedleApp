package moviedleapp.main.controllers

import android.widget.Button
import kotlinx.coroutines.*
import moviedleapp.main.Movie
import moviedleapp.main.Repository
import moviedleapp.main.fragmentListeners.ServerResponseListener


class HomeFragmentController(
    rndMovieButton: Button,
    private val responseListener: ServerResponseListener
) {
    init {
        var randomMovie: Movie
        rndMovieButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                randomMovie = Repository.getRandomMovie()
                withContext(Dispatchers.Main) {
                    responseListener.onReceivingRandomMovie(randomMovie)
                }
            }
        }
    }
}
