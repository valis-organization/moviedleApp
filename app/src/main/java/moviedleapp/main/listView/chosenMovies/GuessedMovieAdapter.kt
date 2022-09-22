package moviedleapp.main.listView.chosenMovies

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import moviedleapp.main.R
import moviedleapp.main.helpers.ResultType

class GuessedMovieAdapter(private val chosenMovies: ArrayList<ChosenMovieModel>) :
    RecyclerView.Adapter<GuessedMovieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ShapeableImageView = view.findViewById(R.id.temp_movie_image)
        val type: TextView = view.findViewById(R.id.temp_type)
        val genre: TextView = view.findViewById(R.id.temp_genre)
        val director: TextView = view.findViewById(R.id.temp_director)
        val rank: TextView = view.findViewById(R.id.temp_rank)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.temp_guessed_movie_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.movieImage.setImageResource(chosenMovies[position].getImageSource())
        viewHolder.type.text = chosenMovies[position].getType()
        determineColour(chosenMovies[position].getType(),viewHolder.type)
        viewHolder.genre.text = chosenMovies[position].getGenre()
        determineColour(chosenMovies[position].getGenre(),viewHolder.genre)
        viewHolder.director.text = chosenMovies[position].getDirector()
        determineColour(chosenMovies[position].getDirector(),viewHolder.director)
        viewHolder.rank.text = chosenMovies[position].getRank()
        determineColour(chosenMovies[position].getRank(),viewHolder.rank)
    }

    override fun getItemCount(): Int {
        return chosenMovies.size
    }

    fun determineColour(resultType: String,view : TextView) {
        when (resultType) {
            ResultType.CORRECT.toString() -> {
                view.setBackgroundColor(Color.parseColor("#228C22"))
            }
            ResultType.PARTIAL.toString() -> {
                view.setBackgroundColor(Color.parseColor("#F7CF1D"))
            }
            ResultType.WRONG.toString() -> {
                view.setBackgroundColor(Color.parseColor("#C61A09"))
            }
        }
    }
}