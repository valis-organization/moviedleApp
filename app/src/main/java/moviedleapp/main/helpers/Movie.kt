package moviedleapp.main.helpers

data class Movie(
    val id: Int = 1,
    val title: String = "UNKNOWN",
    val imageUrl: String = "UNKNOWN",
     val type: String = "UNKNOWN",
    val genre: String = "UNKNOWN",
    val director: String = "UNKNOWN",
    val rank: String = "UNKNOWN",
    val releaseYear: String = "UNKNOWN"
)