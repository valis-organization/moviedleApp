package moviedleapp.main.fragmentListeners

import moviedleapp.main.helpers.Movie

interface ServerResponseListener {

    fun onReceivingRandomMovie(receivedJson: Movie)
}