package moviedleapp.main.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.controllers.HomeFragmentController
import moviedleapp.main.helpers.getDrawableByUrl
import java.io.InputStream
import java.net.URL

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
            lifecycleScope.launch(Dispatchers.IO){
                val randomMovie = controller.getRandomMovie()
                val image = getDrawableByUrl(randomMovie.getImageUrl())
                withContext(Dispatchers.Main){
                    rndMovieTextView.text = randomMovie.getTitle()
                    rndMovieImage.setImageDrawable(image)
                }
            }
        }
    }
}
