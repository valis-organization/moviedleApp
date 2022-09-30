package moviedleapp.main.controllers

import android.graphics.drawable.Drawable
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie

class HomeFragmentController {
    suspend fun getRandomMovie(): Movie {
        return Repository.getRandomMovie()
    }

    fun getImageByTitle(title: String): Drawable? {
        return Repository.getMovieImageByTitle(title)
    }
}
