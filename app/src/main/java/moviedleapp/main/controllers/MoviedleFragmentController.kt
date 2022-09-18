package moviedleapp.main.controllers

import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.helpers.Movie
import moviedleapp.main.Repository
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.RecyclerViewAdapter
import moviedleapp.main.listView.RecyclerViewListener


class MoviedleFragmentController(
    private val moviedleListener: MoviedleListener
) {
    private lateinit var allMovies: ArrayList<Movie>
    init {
        GlobalScope.launch(Dispatchers.IO) {
            allMovies = Repository.getAllMovies()
            withContext(Dispatchers.Main) {
                moviedleListener.addMoviesToListView(allMovies)
                createMovieListView()
            }
        }
    }


    fun createMovieListView() {
        val moviesToChooseView: ArrayList<ListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie

        for (movie in allMovies) {
            moviesToChooseView.add(ListModel(movie.getTitle(), imageTEMP))
        }
        val searchView =  moviedleListener.getSearchView()
        searchView.clearFocus()
        setSearchViewListener(searchView,moviedleListener,moviesToChooseView)
    }

    private fun setSearchViewListener(searchView : SearchView,moviedleListener: MoviedleListener,moviesToChooseView: ArrayList<ListModel>){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.isNotBlank() && doesMovieExists(query, allMovies)) {
                   moviedleListener.guessMovie(query)
                } else {
               //     movieNotFoundInformer.show() TODO
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
          /*          if (filteredMovies.isEmpty() && !movieNotFoundInformer.isShown) { TODO
                        movieNotFoundInformer.show()
                    } else if (filteredMovies.isNotEmpty()) {
                        movieNotFoundInformer.dismiss()
                    }*/
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

