package moviedleapp.main.fragmentListeners

import moviedleapp.main.helpers.Movie
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel

interface MoviedleListener {

    fun showResult(chosenMovie: ChosenMovieModel)

    fun onWinning(title: String)

    fun getMoviesToChoose() : ArrayList<Movie>
}