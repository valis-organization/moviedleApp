package moviedleapp.main.controllers

import android.widget.SearchView
import moviedleapp.main.R
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.RecyclerViewAdapter
import moviedleapp.main.listView.RecyclerViewListener


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener
) {
    fun createMovieListView(allMovies: ArrayList<Movie>) {
        val moviesToChooseView: ArrayList<ListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        for (movie in allMovies) {
            moviesToChooseView.add(ListModel(movie.getTitle(), imageTEMP))
        }
        val searchView = moviedleListener.getSearchView()
        searchView.clearFocus()
        setSearchViewListener(searchView, moviedleListener, moviesToChooseView, allMovies)
    }

    private fun setSearchViewListener(
        searchView: SearchView,
        moviedleListener: MoviedleListener,
        moviesToChooseView: ArrayList<ListModel>,
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

    private fun filterMovies(
        input: String,
        listModelArray: ArrayList<ListModel>
    ): ArrayList<ListModel> {
        val filteredList: ArrayList<ListModel> = ArrayList()
        if (input.isNotBlank()) {
            for (listModel in listModelArray) {
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

