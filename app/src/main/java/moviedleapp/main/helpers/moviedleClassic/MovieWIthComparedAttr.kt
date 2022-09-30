package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.Movie

data class MovieWIthComparedAttr(
    private val movie: Movie = Movie(),
    private val comparedAttributes: ComparedAttributes = ComparedAttributes()
) {

    fun getMovie(): Movie {
        return movie
    }

    fun getComparedAttributes(): ComparedAttributes {
        return comparedAttributes
    }
}