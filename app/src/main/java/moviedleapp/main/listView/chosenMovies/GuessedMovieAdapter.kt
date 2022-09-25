package moviedleapp.main.listView.chosenMovies

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import moviedleapp.main.R
import moviedleapp.main.helpers.Movie
import moviedleapp.main.helpers.ResultType
import moviedleapp.main.helpers.moviedleClassic.ComparedAttributes

class GuessedMovieAdapter(private val chosenMovies: ArrayList<ChosenMovieModel>) :
    RecyclerView.Adapter<GuessedMovieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ShapeableImageView = view.findViewById(R.id.movie_image)
        val title: TextView = view.findViewById(R.id.title)
        val type: TextView = view.findViewById(R.id.type)
        val genre: TextView = view.findViewById(R.id.genre)
        val director: TextView = view.findViewById(R.id.director)
        val rank: TextView = view.findViewById(R.id.rank)
        val release: TextView = view.findViewById(R.id.releaseYear)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.guessed_movie_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movieWIthComparedAttr = chosenMovies[position].getMovieWIthComparedAttr()
        val movie = movieWIthComparedAttr.getMovie()
        val comparedAttr = movieWIthComparedAttr.getComparedAttributes()

        viewHolder.movieImage.setImageResource(chosenMovies[position].getImageSource())
        setAllTexts(viewHolder, movie)
        setAllColours(viewHolder, comparedAttr)

    }

    override fun getItemCount() = chosenMovies.size

    private fun setAllTexts(viewHolder: ViewHolder, movie: Movie) {
        viewHolder.title.text = movie.getTitle()
        viewHolder.type.text = movie.getType()
        viewHolder.genre.text = movie.getGenre()
        viewHolder.director.text = movie.getDirector()
        viewHolder.rank.text = movie.getRank()
        viewHolder.release.text = movie.getReleaseYear()
    }

    private fun setAllColours(viewHolder: ViewHolder, attributes: ComparedAttributes) {
        determineColour(attributes.type, viewHolder.type)
        determineColour(attributes.genre, viewHolder.genre)
        determineColour(attributes.director, viewHolder.director)
        determineColour(attributes.rank, viewHolder.rank)
        determineColour(attributes.releaseYear, viewHolder.release)
    }

    private fun determineColour(resultType: ResultType, view: TextView) {
        when (resultType) {
            ResultType.CORRECT -> {
                view.setBackgroundColor(Color.parseColor("#228C22"))
            }
            ResultType.PARTIAL -> {
                view.setBackgroundColor(Color.parseColor("#F7CF1D"))
            }
            ResultType.WRONG -> {
                view.setBackgroundColor(Color.parseColor("#C61A09"))
            }
            else -> {
                view.setBackgroundColor(Color.parseColor("#C61A09"))
            }
        }
    }
}