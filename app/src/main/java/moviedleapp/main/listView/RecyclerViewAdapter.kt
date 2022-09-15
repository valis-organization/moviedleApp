package moviedleapp.main.listView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import moviedleapp.main.R

class RecyclerViewAdapter(
    private val movies: ArrayList<ListModel>,
    private val recyclerViewListener: RecyclerViewListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View, recyclerViewListener: RecyclerViewListener) :
        RecyclerView.ViewHolder(view) {
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.name)

        init {
            view.setOnClickListener {
                recyclerViewListener.onItemClick(adapterPosition, title.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, recyclerViewListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = movies[position].getTitle()
        viewHolder.movieImage.setImageResource(movies[position].getImage()) // TEMP - >   viewHolder.movieImage.setImageResource(movies[position].getImage())
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}