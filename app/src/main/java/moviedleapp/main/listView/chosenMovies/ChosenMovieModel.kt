package moviedleapp.main.listView.chosenMovies

import android.graphics.drawable.Drawable
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

class ChosenMovieModel(private var imageSource : Drawable,private var movieWIthComparedAttr: MovieWIthComparedAttr) {
    fun getImageSource() =  imageSource
    fun getMovieWIthComparedAttr() = movieWIthComparedAttr
}