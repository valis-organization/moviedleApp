package moviedleapp.main.controllers

import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.getDrawableByUrl
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.helpers.moviedleClassic.areAllAttributesCorrect
import moviedleapp.main.listView.MovieListItem
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel
import java.io.InputStream
import java.net.URL


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener,
    private val fragmentScope: LifecycleCoroutineScope,
) {

    private lateinit var allMovies: ArrayList<Movie>

    suspend fun getAllMovies(): ArrayList<Movie> {
        allMovies = Repository.getAllMovies()
        return allMovies
    }

    fun filterMovies(
        input: String,
        moviesToChoose: ArrayList<MovieListItem>
    ): ArrayList<MovieListItem> {
        val filteredList: ArrayList<MovieListItem> = ArrayList()
        if (input.isNotBlank()) {
            for (movie in moviesToChoose) {
                if (movie.getMovie().getTitle().lowercase().startsWith(input)) {
                    filteredList.add(movie)
                }
            }
        }
        return filteredList
    }

    fun canMovieBeSelected(input: String, moviesList: ArrayList<MovieListItem>): Boolean {
        for (movie in moviesList) {
            if (movie.getMovie().getTitle() == input) {
                return true
            }
        }
        return false
    }

    fun guessMovie(title: String) {
        fragmentScope.launch(Dispatchers.IO) {
            handleResult(Repository.getChosenMovieResult(title))
        }
    }

    fun initMoviesToChooseList(moviesToChoose: ArrayList<MovieListItem>) {
        for (movie in allMovies) {
            fragmentScope.launch(Dispatchers.IO) {
                val image = getDrawableByUrl(movie.getImageUrl())
                withContext(Dispatchers.Default) {
                    moviesToChoose.add(MovieListItem(movie, image))
                }
            }
        }
    }

    fun getMovieImageByTitle(title: String): Drawable? {
        for (movie in moviedleListener.getMoviesToChoose()) {
            if (movie.getMovie().getTitle() == title) {
                return movie.getImage()
            }
        }
        return null
    }

    private fun handleResult(result: MovieWIthComparedAttr) {
        fragmentScope.launch(Dispatchers.IO) {
            val inputStream: InputStream =
                URL(result.getMovie().getImageUrl()).content as InputStream
            val image = Drawable.createFromStream(inputStream, "srcName")
            withContext(Dispatchers.Default) {
                val title = result.getMovie().getTitle()
                val type = result.getMovie().getType()
                val director = result.getMovie().getDirector()
                val genre = result.getMovie().getGenre()
                val rank = result.getMovie().getRank()

                if (isGuessSuccessful(result.getComparedAttributes())) {
                    println("Successfully guessed movie: $title, congratulations!")
                    moviedleListener.onWinning(title)
                } else {
                    println(
                        "Failed to guess movie : ( Your movie: $title \n" +
                                "type: $type\n" +
                                "director: $director\n" +
                                "genre: $genre\n" +
                                "release year: ${result.getComparedAttributes().releaseYear}\n" +
                                "rank: $rank\n"
                    )
                    removeMovieFromSelectingList(title, moviedleListener.getMoviesToChoose())
                }
                moviedleListener.showResult(ChosenMovieModel(image, result))
            }
        }
    }

    private fun removeMovieFromSelectingList(title: String, moviesList: ArrayList<MovieListItem>) {
        var movieToRemove: MovieListItem? = null
        for (movie in moviesList) {
            if (movie.getMovie().getTitle() == title) {
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

