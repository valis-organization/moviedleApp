package moviedleapp.main.controllers

import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.helpers.moviedleClassic.areAllAttributesCorrect
import moviedleapp.main.listView.MovieListModel
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener,
) {
    fun getAllMovies(): ArrayList<Movie> {
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
        movieListModelArray: ArrayList<MovieListModel>
    ): ArrayList<MovieListModel> {
        val filteredList: ArrayList<MovieListModel> = ArrayList()
        if (input.isNotBlank()) {
            for (listModel in movieListModelArray) {
                if (listModel.getTitle().lowercase().startsWith(input)) {
                    filteredList.add(listModel)
                }
            }
        }
        return filteredList
    }

    fun canMovieBeSelected(input: String, moviesList: ArrayList<MovieListModel>): Boolean {
        for (movie in moviesList) {
            if (movie.getTitle() == input) {
                return true
            }
        }
        return false
    }
    fun guessMovie(title: String) {
        handleResult(Repository.getChosenMovieResult(title))
    }

    fun removeMovieFromSelectingList(title: String, moviesList: ArrayList<MovieListModel>) {
        var movieToRemove : MovieListModel? = null
        for (movie in moviesList) {
            if (movie.getTitle() == title) {
                movieToRemove = movie
            }
        }
        if(movieToRemove!= null){
            moviesList.remove(movieToRemove)
        }
    }

    private fun handleResult(result: MovieWIthComparedAttr) {
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        val type = result.getComparedAttributes().type.toString()
        val director = result.getComparedAttributes().director.toString()
        val genre = result.getComparedAttributes().genre.toString()
        val rank = result.getComparedAttributes().rank.toString()

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
        moviedleListener.showResult(ChosenMovieModel(imageTEMP, type, genre, director, rank))
    }

}

