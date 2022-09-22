package moviedleapp.main.fragmentListeners

import moviedleapp.main.listView.chosenMovies.ChosenMovieModel

interface MoviedleListener {

   fun showResult(chosenMovie: ChosenMovieModel)

   fun onWinning()
}