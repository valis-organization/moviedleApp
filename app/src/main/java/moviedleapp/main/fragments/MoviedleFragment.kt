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
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.listView.MoviesToChooseViewAdapter
import moviedleapp.main.listView.MoviesToChooseViewListener
import moviedleapp.main.listView.chosenMovies.GuessedMovieAdapter


class MoviedleFragment : Fragment() {

    //VIEWS
    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenMoviesListView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var movieNotFoundInformer: TextView
    private lateinit var controller: MoviedleFragmentController
    private lateinit var winningTextView: TextView
    private lateinit var winningMovie: ShapeableImageView

    //LISTS
    private val chosenMoviesArrayList: ArrayList<MovieWIthComparedAttr> = ArrayList()
    private val moviesToChoose: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moviedle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignViews()

        val moviedleListener = object : MoviedleListener {
            override fun showResult(chosenMovie: MovieWIthComparedAttr) {
                chosenMoviesArrayList.add(chosenMovie)
                lifecycleScope.launch(Dispatchers.Main) {
                    chosenMoviesListView.adapter = GuessedMovieAdapter(chosenMoviesArrayList)
                }
            }

            override fun onWinning(title: String) {
                lifecycleScope.launch(Dispatchers.Main) {
                    searchView.visibility = View.INVISIBLE
                    winningMovie.setImageDrawable(controller.getMovieImageByTitle(title))
                    winningTextView.visibility = View.VISIBLE
                    winningMovie.visibility = View.VISIBLE
                }
            }

            override fun getMoviesToChoose(): ArrayList<Movie> {
                return moviesToChoose
            }
        }

        controller = MoviedleFragmentController(moviedleListener, lifecycleScope)
        lifecycleScope.launch {
            controller.initMoviesToChooseList(moviesToChoose)
            createMovieListView()
        }
    }

    private fun createMovieListView() {
        searchView.clearFocus()

        val adapter = MoviesToChooseViewAdapter(
            moviesToChoose,
            object : MoviesToChooseViewListener {
                override fun onItemClick(position: Int, title: String) {
                    println("Item $position clicked.")
                    println("Item name: $title")
                    searchView.setQuery(title, false)
                }
            }
        )
        setSearchViewListener(searchView, moviesToChoose, adapter)
    }

    private fun setSearchViewListener(
        searchView: SearchView,
        moviesToChoose: ArrayList<Movie>,
        adapter: MoviesToChooseViewAdapter
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.isNotBlank() && controller.canMovieBeSelected(
                        query,
                        moviesToChoose
                    )
                ) {
                    controller.guessMovie(query)
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
                    adapter.setMovies(filteredMovies)
                    recyclerView.adapter = adapter
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

        winningMovie = requireView().findViewById(R.id.winning_image)
        winningTextView = requireView().findViewById(R.id.winning_text_view)
    }

    private fun showMovieNotFoundNotification() {
        movieNotFoundInformer.visibility = View.VISIBLE
    }

    private fun hideMovieNotFoundNotification() {
        movieNotFoundInformer.visibility = View.INVISIBLE
    }

}