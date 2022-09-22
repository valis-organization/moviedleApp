package moviedleapp.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.Movie
import moviedleapp.main.listView.MovieListModel
import moviedleapp.main.listView.RecyclerViewAdapter
import moviedleapp.main.listView.RecyclerViewListener
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel
import moviedleapp.main.listView.chosenMovies.GuessedMovieAdapter

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenMoviesListView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var movieNotFoundInformer: TextView
    private lateinit var controller: MoviedleFragmentController
    private val chosenMoviesArrayList: ArrayList<ChosenMovieModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moviedle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var allMovies: ArrayList<Movie>
        assignViews()

        val moviedleListener = object : MoviedleListener {
            override fun showResult(chosenMovie: ChosenMovieModel) {
                chosenMoviesArrayList.add(chosenMovie)
                lifecycleScope.launch(Dispatchers.Main) {
                    chosenMoviesListView.adapter = GuessedMovieAdapter(chosenMoviesArrayList)
                }
            }

            override fun onWinning() {
                lifecycleScope.launch(Dispatchers.Main){
                    searchView.clearFocus()
                    searchView.isEnabled = false
                    searchView.isSubmitButtonEnabled = false
                }
            }
        }

        controller = MoviedleFragmentController(moviedleListener)
        lifecycleScope.launch(Dispatchers.IO) {
            allMovies = controller.getAllMovies()
            withContext(Dispatchers.Main) {
                createMovieListView(allMovies)
            }
        }

    }

    private fun createMovieListView(allMovies: ArrayList<Movie>) {
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        val moviesToChoose: ArrayList<MovieListModel> = ArrayList()
        for (movie in allMovies) {
            moviesToChoose.add(MovieListModel(movie.getTitle(), imageTEMP))
        }
        searchView.clearFocus()
        setSearchViewListener(searchView, moviesToChoose)
    }

    private fun setSearchViewListener(
        searchView: SearchView,
        moviesToChoose: ArrayList<MovieListModel>
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.isNotBlank() && controller.canMovieBeSelected(
                        query,
                        moviesToChoose
                    )
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        controller.guessMovie(query)
                    }
                    controller.removeMovieFromSelectingList(query, moviesToChoose)
                    searchView.setQuery("", false)
                } else {
                    showMovieNotFoundNotification()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredMovies =
                        controller.filterMovies(newText.lowercase(), moviesToChoose)

                    val recyclerViewListener = object : RecyclerViewListener {
                        override fun onItemClick(position: Int, title: String) {
                            println("Item $position clicked.")
                            println("Item name: $title")
                            searchView.setQuery(title, false)
                        }
                    }
                    recyclerView.adapter =
                        RecyclerViewAdapter(
                            filteredMovies,
                            recyclerViewListener
                        )
                    if (filteredMovies.isEmpty() && newText.isNotBlank()) {
                        showMovieNotFoundNotification()
                    } else if (filteredMovies.isNotEmpty()) {
                        hideMovieNotFoundNotification()
                    }
                }
                return true
            }
        })
    }

    private fun assignViews() {
        searchView = requireView().findViewById(R.id.search_view)
        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        chosenMoviesListView = requireView().findViewById(R.id.guessed_movies)
        chosenMoviesListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

        movieNotFoundInformer = requireView().findViewById(R.id.movie_not_found_view)
    }

    private fun showMovieNotFoundNotification() {
        movieNotFoundInformer.visibility = View.VISIBLE
    }

    private fun hideMovieNotFoundNotification() {
        movieNotFoundInformer.visibility = View.INVISIBLE
    }

}