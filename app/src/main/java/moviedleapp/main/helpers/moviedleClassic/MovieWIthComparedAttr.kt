package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.Movie

class MovieWIthComparedAttr(
    private val movie: Movie,
    private val comparedAttributes: ComparedAttributes
) {

    fun getMovie(): Movie {
        return movie
    }

    fun getComparedAttributes(): ComparedAttributes {
        return comparedAttributes
    }
}