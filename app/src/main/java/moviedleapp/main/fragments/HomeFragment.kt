package moviedleapp.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.controllers.HomeFragmentController
import java.net.SocketTimeoutException

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rndMovieButton = requireView().findViewById<Button>(R.id.button_rndMovie)
        val rndMovieTextView = requireView().findViewById<TextView>(R.id.text_view_rnd_movie)
        val rndMovieImage = requireView().findViewById<ImageView>(R.id.movie_image)
        val controller = HomeFragmentController()

        rndMovieButton.setOnClickListener {
            lifecycleScope.launch{
                try{
                    val randomMovie = controller.getRandomMovie()
                    withContext(Dispatchers.Main){
                        rndMovieTextView.text = randomMovie.title
                        rndMovieImage.setImageDrawable(controller.getImageByTitle(randomMovie.title))
                    }
                }catch (e: SocketTimeoutException){
                    Log.e("Error","${e.message}")
                    Toast.makeText(requireContext(),"Connection problems...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
