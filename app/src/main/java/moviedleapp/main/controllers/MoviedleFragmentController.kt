package moviedleapp.main.controllers

import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.helpers.moviedleClassic.areAllAttributesCorrect
import moviedleapp.main.listView.MovieListItem
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener,
) {
    suspend fun getAllMovies(): ArrayList<Movie> {
        return Repository.getAllMovies()
    }

    private fun isGuessSuccessful(attributes: ComparedAttributes): Boolean {
        if (areAllAttributesCorrect(attributes)) {
            return true
        }
        return false
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

    suspend fun guessMovie(title: String) {
        handleResult(Repository.getChosenMovieResult(title))
    }

    fun removeMovieFromSelectingList(title: String, moviesList: ArrayList<MovieListItem>) {
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

    private fun handleResult(result: MovieWIthComparedAttr) {
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        val type = result.getMovie().getType()
        val director = result.getMovie().getDirector()
        val genre = result.getMovie().getGenre()
        val rank = result.getMovie().getRank()

        if (isGuessSuccessful(result.getComparedAttributes())) {
            println("Successfully guessed movie: ${result.getMovie().getTitle()}, congratulations!")
            moviedleListener.onWinning()
        } else {
            println(
                "Failed to guess movie : ( Your movie: ${result.getMovie().getTitle()} \n" +
                        "type: $type\n" +
                        "director: $director\n" +
                        "genre: $genre\n" +
                        "release year: ${result.getComparedAttributes().releaseYear}\n" +
                        "rank: $rank\n"
            )
        }
        moviedleListener.showResult(ChosenMovieModel(imageTEMP,result))
    }

}

