package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import moviedleapp.main.Movie
import moviedleapp.main.MoviedleListener
import moviedleapp.main.R
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.ListViewAdapter

class MoviedleFragment : Fragment() {

    private var listView :ListView? = null

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
            createMoviesListView(moviesList)
            }
        }
        val fragmentController = MoviedleFragmentController(moviedleListener)
        fragmentController.initialize()
    }

    private fun createMoviesListView(moviesList: ArrayList<Movie>) {
        val listModelArray: ArrayList<ListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie
        listView = requireView().findViewById(R.id.list_view)


        for (movie in moviesList) {
            listModelArray.add(ListModel(movie.getTitle(), imageTEMP))
        }
        val adapter = ListViewAdapter(requireContext(),listModelArray)

        listView!!.adapter = adapter
    }

}