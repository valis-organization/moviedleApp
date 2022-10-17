package moviedleapp.main.helpers

import android.util.Log
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

object Logger {

    const val logTag = "LOG"

    fun logReceivedMovies() = Log.i(logTag, "Received all movies from server.")

    fun logRandomMovie(title: String) = Log.i(logTag, "Received random movie from server - $title")

    fun logReceivedResult() = Log.i(logTag, "Received result from server")

    fun logItemClicked(position: Int, title: String) =
        Log.i(logTag, "Movie on position $position clicked - Movie name: $title")

    fun logFailedGuessing(result: MovieWIthComparedAttr) {
        Log.i(
            logTag, "Failed to guess movie. \n" +
                    "Chosen movie - ${result.movie} \n" +
                    "Compared attributes: ${result.comparedAttributes} "
        )
    }

    fun logSuccessfulGuessing(result: MovieWIthComparedAttr) {
        Log.i(
            logTag, "Successfully guessed correct movie. \n" +
                    "Chosen movie - ${result.movie} \n" +
                    "Compared attributes: ${result.comparedAttributes} "
        )
    }
}