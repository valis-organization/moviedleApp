package moviedleapp.main.controllers

import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie


class HomeFragmentController {
    suspend fun getRandomMovie(): Movie {
        return Repository.getRandomMovie()
    }
}
