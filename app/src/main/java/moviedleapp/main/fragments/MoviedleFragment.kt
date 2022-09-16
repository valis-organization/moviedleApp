package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.RecyclerViewAdapter
import moviedleapp.main.listView.RecyclerViewListener

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var chosenMovie: MovieWIthComparedAttr
    private lateinit var movieNotFoundInformer: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moviedle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviedleListener = object : MoviedleListener {
            override fun addMoviesToListView(moviesList: ArrayList<Movie>) {
                createMovieListView(moviesList)
            }
        }
        movieNotFoundInformer = Snackbar.make(view, "No movie found", Snackbar.LENGTH_SHORT)
        val fragmentController = MoviedleFragmentController(moviedleListener)
    }

    private fun createMovieListView(moviesList: ArrayList<Movie>) {
        val listModelArray: ArrayList<ListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie

        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        for (movie in moviesList) {
            listModelArray.add(ListModel(movie.getTitle(), imageTEMP))

        }

        searchView = requireView().findViewById(R.id.search_view)
        searchView.clearFocus()
        val recyclerViewListener = object : RecyclerViewListener {
            override fun onItemClick(position: Int, title: String) {
                println("Item $position clicked.")
                println("Item name: $title")
                searchView.setQuery(title, false)
            }
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.isNotBlank() && doesMovieExists(query, moviesList)) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        chosenMovie = Repository.guessMovie(query)
                    }
                } else {
                    movieNotFoundInformer.show()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredMovies = filterMovies(newText.lowercase(), listModelArray)
                    recyclerView.adapter =
                        RecyclerViewAdapter(
                            filteredMovies,
                            recyclerViewListener
                        )
                    if (filteredMovies.isEmpty() && !movieNotFoundInformer.isShown) {
                        movieNotFoundInformer.show()
                    } else if (filteredMovies.isNotEmpty()) {
                        movieNotFoundInformer.dismiss()
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