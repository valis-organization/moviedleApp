package moviedleapp.main.fragmentListeners

import moviedleapp.main.Movie

interface ServerResponseListener {

    fun onReceivingRandomMovie(receivedJson: Movie)
}