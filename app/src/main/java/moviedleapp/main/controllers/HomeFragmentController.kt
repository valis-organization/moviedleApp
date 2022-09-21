package moviedleapp.main.controllers

import android.widget.Button
import moviedleapp.main.fragmentListeners.HomeListener


class HomeFragmentController(
    rndMovieButton: Button,
    private val responseListener: HomeListener
) {
    init {
        rndMovieButton.setOnClickListener {
            responseListener.getAndShowRndMovie()
        }
    }
}
