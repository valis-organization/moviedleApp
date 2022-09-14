package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moviedleapp.main.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.R
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.RecyclerViewAdapter

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    recyclerView.adapter =
                        RecyclerViewAdapter(filterMovies(newText, listModelArray))
                } else {
                    Toast.makeText(activity, "No movie found", Toast.LENGTH_SHORT).show()
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
                if (listModel.getTitle().startsWith(input.lowercase())) {
                    filteredList.add(listModel)
                }
            }
        }
        return filteredList
    }

}