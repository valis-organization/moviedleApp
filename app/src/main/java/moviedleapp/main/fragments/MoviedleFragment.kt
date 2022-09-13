package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moviedleapp.main.Movie
import moviedleapp.main.fragmentListeners.MoviedleListener
import moviedleapp.main.R
import moviedleapp.main.controllers.MoviedleFragmentController
import moviedleapp.main.listView.ListModel
import moviedleapp.main.listView.RecyclerViewAdapter

class MoviedleFragment : Fragment() {

    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

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
    }

    private fun createMoviesListView(moviesList: ArrayList<Movie>) {
        iterateMoviesTEMP(moviesList) // TEMP
        val listModelArray: ArrayList<ListModel> = ArrayList()
        val imageTEMP =
            R.drawable.place_holder_nodpi //TEMP, in future change it to image assigned to the movie

        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
       // recyclerView.setHasFixedSize(true)


        for (movie in moviesList) {
            listModelArray.add(ListModel(movie.getTitle(), imageTEMP))
        }
        println("List model size ${listModelArray.size}")
       // val adapter = ListViewAdapter(requireContext(),listModelArray)
       // adapter = RecyclerViewAdapter(listModelArray)
        recyclerView.adapter = RecyclerViewAdapter(listModelArray)
    }

    private fun iterateMoviesTEMP(movies : ArrayList<Movie>){
        for(movie in movies){
            println(movie.getTitle())
        }
        println("Movies list size ${movies.size}")
    }
}