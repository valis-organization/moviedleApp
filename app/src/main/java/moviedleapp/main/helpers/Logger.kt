package moviedleapp.main.helpers

import android.util.Log
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

object Logger {

    fun logReceivedMovies() = Log.e("Logowanie","Received all movies from server.")

    fun logRandomMovie(title: String) = println("Received random movie from server - $title")

    fun logReceivedResult() = println("Received result from server")

    fun logItemClicked(position: Int, title: String) =
        println("Movie on position $position clicked - Movie name: $title")

    fun logFailedGuessing(result : MovieWIthComparedAttr){
        println("Failed to guess movie. \n"+
                "Chosen movie - ${result.getMovie()} \n" +
                "Compared attributes: ${result.getComparedAttributes()} ")
    }

    fun logSuccessfulGuessing(result: MovieWIthComparedAttr){
        println("Successfully guessed correct movie. \n" +
                "Chosen movie - ${result.getMovie()} \n" +
                "Compared attributes: ${result.getComparedAttributes()} ")
    }
}