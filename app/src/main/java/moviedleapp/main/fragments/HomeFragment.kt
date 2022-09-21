package moviedleapp.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.helpers.Movie
import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.fragmentListeners.HomeListener
import moviedleapp.main.controllers.HomeFragmentController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = requireView().findViewById<Button>(R.id.button_rndMovie)
        val rndMovieTextView = requireView().findViewById<TextView>(R.id.text_view_rnd_movie)
        val homeListener = object : HomeListener {
            override fun getAndShowRndMovie() {
                var randomMovie : Movie
                lifecycleScope.launch(Dispatchers.IO) {
                    randomMovie = Repository.getRandomMovie()
                    withContext(Dispatchers.Main) {
                        rndMovieTextView.text = randomMovie.getTitle()
                    }
                }
            }
        }
        val fragmentController = HomeFragmentController(button, homeListener)
    }
}