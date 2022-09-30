package moviedleapp.main.fragmentListeners

import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

interface MoviedleListener {

    fun showResult(chosenMovie: MovieWIthComparedAttr)

    fun onWinning(title: String)

    fun getMoviesToChoose() : ArrayList<Movie>
}