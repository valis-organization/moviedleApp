package moviedleapp.main.fragmentListeners

import android.graphics.drawable.Drawable
import moviedleapp.main.listView.MovieListItem
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel

interface MoviedleListener {

    fun showResult(chosenMovie: ChosenMovieModel)

    fun onWinning(title: String)

    fun getMoviesToChoose() : ArrayList<MovieListItem>
}