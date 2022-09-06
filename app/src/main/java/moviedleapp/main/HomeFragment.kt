package moviedleapp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.gson.Gson
import moviedleapp.main.controllers.HomeFragmentController
import okhttp3.OkHttpClient

class HomeFragment : Fragment() {

    private val client = OkHttpClient()
    private val rndMovieUrl = "http://109.207.149.50:8080/randomMovie"
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = requireView().findViewById<Button>(R.id.button_rndMovie)
        val rndMovieTextView = requireView().findViewById<TextView>(R.id.text_view_rnd_movie)
        val serverResponseListener = object : ServerResponseListener {
            override fun onReceivingRandomMovie(receivedJson: Movie) {
                runOnUiThread {
                    //TODO
                  //  rndMovieTextView.text = receivedJson.title
                }
            }
        }
        val fragmentController: HomeFragmentController =
            HomeFragmentController(button, serverResponseListener)
        fragmentController.initialize()
    }
}