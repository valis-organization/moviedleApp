package moviedleapp.main.listView.chosenMovies

class ChosenMovieModel(private var imageSource : Int,private var type: String, private var genre: String,private var director: String,private var rank : String ) {
    fun getImageSource(): Int{
        return imageSource
    }
    fun getType(): String {
        return type
    }
    fun getGenre(): String {
        return genre
    }
    fun getDirector(): String{
        return director
    }
    fun getRank(): String{
        return rank
    }
}