package moviedleapp.main.helpers

const val baseUrl = "http://109.207.149.50:8080"
const val randomMovieUrl =  "$baseUrl/randomMovie"
const val allMovies = "$baseUrl/moviesList"
fun guessMovie(movieTitle: String) = "$baseUrl/games/classic/guess/$movieTitle"