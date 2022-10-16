package moviedleapp.main.controllers

import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.Logger
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.helpers.moviedleClassic.areAllAttributesCorrect

class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener,
    private val fragmentScope: LifecycleCoroutineScope
) {
    private val good: String = "\uD83D\uDFE9"
    private val bad : String = "\uD83D\uDFE5"
    private val attempts: ArrayList<ComparedAttributes> = ArrayList()


    fun filterMovies(
        input: String,
        moviesToChoose: ArrayList<Movie>
    ): ArrayList<Movie> {
        val filteredList: ArrayList<Movie> = ArrayList()
        if (input.isNotBlank()) {
            for (movie in moviesToChoose) {
                if (movie.title.lowercase().startsWith(input)) {
                    filteredList.add(movie)
                }
            }
        }
        return filteredList
    }

    fun canMovieBeSelected(input: String, moviesList: ArrayList<Movie>): Boolean {
        for (movie in moviesList) {
            if (movie.title == input) {
                return true
            }
        }
        return false
    }

    suspend fun initMoviesToChooseList(moviesToChoose: ArrayList<Movie>) {
        for (movie in Repository.getAllMovies()) {
            moviesToChoose.add(movie)
        }
    }

    fun guessMovie(title: String) {
        fragmentScope.launch {
            handleResult(Repository.getChosenMovieResult(title))
        }
    }

    fun getMovieImageByTitle(title: String): Drawable? {
        return Repository.getMovieImageByTitle(title)
    }

    private fun handleResult(result: MovieWIthComparedAttr) {
        val title = result.movie.title
        attempts.add(result.comparedAttributes)

        if (isGuessSuccessful(result.comparedAttributes)) {
            Logger.logSuccessfulGuessing(result)
            moviedleListener.onWinning(title)
        } else {
            Logger.logFailedGuessing(result)
            removeMovieFromSelectingList(title, moviedleListener.getMoviesToChoose())
        }
        moviedleListener.showResult(result)


    }

    private fun removeMovieFromSelectingList(title: String, moviesList: ArrayList<Movie>) {
        var movieToRemove: Movie? = null
        for (movie in moviesList) {
            if (movie.title == title) {
                movieToRemove = movie
            }
        }
        if (movieToRemove != null) {
            moviesList.remove(movieToRemove)
        }
    }

    private fun isGuessSuccessful(attributes: ComparedAttributes): Boolean {
        if (areAllAttributesCorrect(attributes)) {
            return true
        }
        return false
    }
}

