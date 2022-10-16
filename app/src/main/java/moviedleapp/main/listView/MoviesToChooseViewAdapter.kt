package moviedleapp.main.listView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import moviedleapp.main.R
import moviedleapp.main.Repository
import moviedleapp.main.helpers.Movie

class MoviesToChooseViewAdapter(
    private var movies: ArrayList<Movie>,
    private val moviesToChooseViewListener: MoviesToChooseViewListener,
) : RecyclerView.Adapter<MoviesToChooseViewAdapter.ViewHolder>() {

    class ViewHolder(view: View, moviesToChooseViewListener: MoviesToChooseViewListener) :
        RecyclerView.ViewHolder(view) {
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.name)

        init {
            view.setOnClickListener {
                moviesToChooseViewListener.onItemClick(adapterPosition, title.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return ViewHolder(itemView, moviesToChooseViewListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movieTitle = movies[position].title

        viewHolder.title.text = movieTitle
        viewHolder.movieImage.setImageDrawable(Repository.getMovieImageByTitle(movieTitle))
    }

    override fun getItemCount() = movies.size

    fun setMovies(filteredMovies: ArrayList<Movie>) {
        movies = filteredMovies
    }

}