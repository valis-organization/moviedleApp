package moviedleapp.main.fragmentListeners

import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import moviedleapp.main.helpers.Movie

interface MoviedleListener {

    fun guessMovie(title: String)

    fun getSearchView(): SearchView

    fun getRecyclerView() : RecyclerView

    fun getChosenMoviesListView() : RecyclerView

    fun showMovieNotFoundNotification()

    fun hideMovieNotFoundNotification()
}