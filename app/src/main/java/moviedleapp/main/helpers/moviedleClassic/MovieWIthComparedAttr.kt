package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.Movie

data class MovieWIthComparedAttr(
    val movie: Movie = Movie(),
    val comparedAttributes: ComparedAttributes = ComparedAttributes()

)