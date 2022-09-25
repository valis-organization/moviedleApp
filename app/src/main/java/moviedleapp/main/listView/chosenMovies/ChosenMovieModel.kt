package moviedleapp.main.listView.chosenMovies

import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

class ChosenMovieModel(private var imageSource : Int,private var movieWIthComparedAttr: MovieWIthComparedAttr) {
    fun getImageSource() =  imageSource
    fun getMovieWIthComparedAttr() = movieWIthComparedAttr
}