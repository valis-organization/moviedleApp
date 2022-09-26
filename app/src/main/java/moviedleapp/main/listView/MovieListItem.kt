package moviedleapp.main.listView

import android.graphics.drawable.Drawable
import moviedleapp.main.helpers.Movie

class MovieListItem(private val movie : Movie,private val image : Drawable){
    fun getMovie() = movie

    fun getImage() = image
}