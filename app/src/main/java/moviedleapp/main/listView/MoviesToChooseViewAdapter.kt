package moviedleapp.main.listView

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.*
import moviedleapp.main.R
import moviedleapp.main.helpers.Movie
import java.io.InputStream
import java.net.URL

class MoviesToChooseViewAdapter(
    private var movies: ArrayList<MovieListItem>,
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
        viewHolder.title.text = movies[position].getMovie().getTitle()
        viewHolder.movieImage.setImageDrawable(movies[position].getImage())
/*        fragmentScope.launch(Dispatchers.IO){
            val inputStream: InputStream = URL().content as InputStream
            val d = Drawable.createFromStream(inputStream,"srcName")
            withContext(Dispatchers.Main){

            }
        }*/
    }

    override fun getItemCount() = movies.size

    fun setMovies(filteredMovies : ArrayList<MovieListItem>){
        movies = filteredMovies
    }

}