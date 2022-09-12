package moviedleapp.main.controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.Movie
import moviedleapp.main.Repository
import moviedleapp.main.fragmentListeners.MoviedleListener


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener
) {
    init {
        var allMovies: ArrayList<Movie>
        GlobalScope.launch(Dispatchers.IO) {
            allMovies = Repository.getAllMovies()
            withContext(Dispatchers.Main) {
                moviedleListener.addMoviesToListView(allMovies)
            }
        }
    }
}

