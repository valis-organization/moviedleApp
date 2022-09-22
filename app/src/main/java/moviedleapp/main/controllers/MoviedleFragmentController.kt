package moviedleapp.main.controllers

import android.view.View
import android.widget.SearchView
import moviedleapp.main.R
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.GuessResultHelper
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.listView.MovieListModel
import moviedleapp.main.listView.RecyclerViewAdapter
import moviedleapp.main.listView.RecyclerViewListener


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener
) {
    fun createMovieListView(allMovies: ArrayList<Movie>) {
        val moviesToChooseView: ArrayList<MovieListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        for (movie in allMovies) {
            moviesToChooseView.add(MovieListModel(movie.getTitle(), imageTEMP))
        }
        val searchView = moviedleListener.getSearchView()
        searchView.clearFocus()
        setSearchViewListener(searchView, moviedleListener, moviesToChooseView, allMovies)
    }

    fun handleResult(result: MovieWIthComparedAttr) {
        if(isGuessSuccessful(result.getComparedAttributes())){
            println("Successfully guessed movie: ${result.getMovie().getTitle()}, congratulations!")
             moviedleListener.getSearchView().visibility = View.GONE
        }else{
            println("Unlucky :/" +
                    "type: ${result.getComparedAttributes().type}" +
                    "director: ${result.getComparedAttributes().director}" +
                    "genre: ${result.getComparedAttributes().genre}" +
                    "release year: ${result.getComparedAttributes().releaseYear}" +
                    "type: ${result.getComparedAttributes().rank
                    }"
            )
        }

    }

    private fun setSearchViewListener(
        searchView: SearchView,
        moviedleListener: MoviedleListener,
        moviesToChooseView: ArrayList<MovieListModel>,
        allMovies: ArrayList<Movie>,
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.isNotBlank() && doesMovieExists(query, allMovies)) {
                    moviedleListener.guessMovie(query)
                } else {
                    moviedleListener.showMovieNotFoundNotification()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredMovies = filterMovies(newText.lowercase(), moviesToChooseView)

                    val recyclerViewListener = object : RecyclerViewListener {
                        override fun onItemClick(position: Int, title: String) {
                            println("Item $position clicked.")
                            println("Item name: $title")
                            searchView.setQuery(title, false)
                        }
                    }
                    moviedleListener.getRecyclerView().adapter =
                        RecyclerViewAdapter(
                            filteredMovies,
                            recyclerViewListener
                        )
                    if (filteredMovies.isEmpty() && newText.isNotBlank()) {
                        moviedleListener.showMovieNotFoundNotification()
                    } else if (filteredMovies.isNotEmpty()) {
                        moviedleListener.hideMovieNotFoundNotification()
                    }
                }
                return true
            }
        })
    }

    private fun isGuessSuccessful(attributes: ComparedAttributes): Boolean {
        if(GuessResultHelper.areAllAttributesCorrect(attributes)){
            return true
        }
        return false
    }

    private fun filterMovies(
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

    private fun doesMovieExists(input: String, moviesList: ArrayList<Movie>): Boolean {
        for (movie in moviesList) {
            if (movie.getTitle() == input) {
                return true
            }
        }
        return false
    }

}

