package moviedleapp.main.helpers

class EndpointUrl {
    companion object {
        fun randomMovieUrl() = "http://109.207.149.50:8080/randomMovie"
        fun allMovies() = "http://109.207.149.50:8080/moviesList"
        fun guessMovie(movieTitle: String) = "http://109.207.149.50:8080/games/classic/guess/$movieTitle"
    }
}