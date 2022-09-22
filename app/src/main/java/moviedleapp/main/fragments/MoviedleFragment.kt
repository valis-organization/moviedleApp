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
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes
import moviedleapp.main.helpers.moviedleClassic.GuessResultHelper
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr
import moviedleapp.main.listView.chosenMovies.ChosenMovieModel
import moviedleapp.main.listView.chosenMovies.GuessedMovieAdapter

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenMoviesListView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var chosenMovie: MovieWIthComparedAttr
    private lateinit var movieNotFoundInformer: TextView
    private var chosenMoviesArrayList: ArrayList<ChosenMovieModel> = ArrayList()

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

        chosenMoviesListView = requireView().findViewById(R.id.guessed_movies)
        chosenMoviesListView.layoutManager = LinearLayoutManager(activity)

        movieNotFoundInformer = requireView().findViewById(R.id.movie_not_found_view)
        val moviedleListener = object : MoviedleListener {

            override fun getSearchView(): SearchView {
                return searchView
            }

            override fun getRecyclerView(): RecyclerView {
                return recyclerView
            }

            override fun getChosenMoviesListView(): RecyclerView {
                return chosenMoviesListView
            }

            override fun showMovieNotFoundNotification() {
                movieNotFoundInformer.visibility = View.VISIBLE
            }

            override fun hideMovieNotFoundNotification() {
                movieNotFoundInformer.visibility = View.INVISIBLE
            }

            override fun guessMovie(title: String) {
                lifecycleScope.launch(Dispatchers.IO) {
                    chosenMovie = Repository.getChosenMovieResult(title)
                    handleResult(chosenMovie)
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

    fun handleResult(result: MovieWIthComparedAttr) {
        if (isGuessSuccessful(result.getComparedAttributes())) {
            println("Successfully guessed movie: ${result.getMovie().getTitle()}, congratulations!")
            lifecycleScope.launch(Dispatchers.Main) {
                searchView.visibility = View.GONE
            }
        } else {
            val imageTEMP =
                R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
            val type = result.getComparedAttributes().type.toString()
            val director = result.getComparedAttributes().director.toString()
            val genre = result.getComparedAttributes().genre.toString()
            val rank = result.getComparedAttributes().rank.toString()

            println(
                "Failed to guess movie : ( Your movie: ${result.getMovie().getTitle()} \n" +
                        "type: ${result.getComparedAttributes().type}\n" +
                        "director: ${result.getComparedAttributes().director}\n" +
                        "genre: ${result.getComparedAttributes().genre}\n" +
                        "release year: ${result.getComparedAttributes().releaseYear}\n" +
                        "rank: ${result.getComparedAttributes().rank}\n"
            )
            chosenMoviesArrayList.add(ChosenMovieModel(imageTEMP,type, genre, director, rank))
            lifecycleScope.launch(Dispatchers.Main){
                chosenMoviesListView.adapter = GuessedMovieAdapter(chosenMoviesArrayList)
            }
        }
    }

    private fun isGuessSuccessful(attributes: ComparedAttributes): Boolean {
        if (GuessResultHelper.areAllAttributesCorrect(attributes)) {
            return true
        }
        return false
    }

}