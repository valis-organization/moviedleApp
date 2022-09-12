package moviedleapp.main.fragmentListeners

import moviedleapp.main.Movie

interface MoviedleListener {
    fun addMoviesToListView(moviesList : ArrayList<Movie>)
}