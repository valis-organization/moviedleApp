package moviedleapp.main

interface ServerResponseListener {

    fun onReceivingRandomMovie(receivedJson: Movie)
}