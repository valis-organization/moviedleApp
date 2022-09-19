package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.helpers.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var chosenMovie: MovieWIthComparedAttr
    private lateinit var movieNotFoundInformer: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moviedle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var allMovies: ArrayList<Movie>
        searchView = requireView().findViewById(R.id.search_view)
        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        movieNotFoundInformer = requireView().findViewById(R.id.movie_not_found_view)
        val moviedleListener = object : MoviedleListener {

            override fun getSearchView(): SearchView {
                return searchView
            }

            override fun getRecyclerView(): RecyclerView {
                return recyclerView
            }

            override fun showMovieNotFoundNotification() {
                movieNotFoundInformer.visibility = View.VISIBLE
            }

            override fun hideMovieNotFoundNotification() {
                movieNotFoundInformer.visibility = View.GONE
            }

            override fun guessMovie(title: String) {
                lifecycleScope.launch(Dispatchers.IO) {
                    chosenMovie = Repository.getChosenMovieResult(title)
                }
            }
        }
        val fragmentController = MoviedleFragmentController(moviedleListener)
        lifecycleScope.launch(Dispatchers.IO) {
            allMovies = Repository.getAllMovies()
            withContext(Dispatchers.Main) {
                fragmentController.createMovieListView(allMovies)
            }
        }

    }

}