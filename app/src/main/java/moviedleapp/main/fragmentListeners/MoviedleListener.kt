package moviedleapp.main.fragmentListeners

import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import moviedleapp.main.helpers.Movie

interface MoviedleListener {
    fun addMoviesToListView(allMovies: ArrayList<Movie>)

    fun guessMovie(title: String)

    fun getSearchView(): SearchView

    fun getRecyclerView() : RecyclerView

}