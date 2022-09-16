package moviedleapp.main.fragmentListeners

import moviedleapp.main.helpers.Movie

interface MoviedleListener {
    fun addMoviesToListView(moviesList : ArrayList<Movie>)
}