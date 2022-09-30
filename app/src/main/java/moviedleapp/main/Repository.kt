package moviedleapp.main

import android.graphics.drawable.Drawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.getDrawableByUrl
import moviedleapp.main.helpers.moviedleClassic.MovieWIthComparedAttr

object Repository {

    private var allMovies = ArrayList<Movie>()
    private var moviesImage = HashMap<String, Drawable>()
    private var repositoryService = RepositoryService()

    suspend fun getAllMovies(): ArrayList<Movie> {
        if (allMovies.isEmpty()) {
            allMovies = repositoryService.getAllMovies()
            initializeMoviesImage()
        }
        println(allMovies.size)
        return allMovies
    }



    suspend fun getRandomMovie() : Movie{
        return repositoryService.getRandomMovie()
    }

    fun getMovieImageByTitle(title: String): Drawable? {
        return moviesImage[title]
    }

    private fun initializeMoviesImage() {
        for (movie in allMovies) {
            CoroutineScope(Dispatchers.IO).launch {
                val image = getDrawableByUrl(movie.getImageUrl())
                withContext(Dispatchers.Default) {
                    moviesImage[movie.getTitle()] = image
                }
            }
        }
    }

    suspend fun getChosenMovieResult(title: String): MovieWIthComparedAttr {
        return repositoryService.getChosenMovieResult(title)
    }
}