package moviedleapp.main.listView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import moviedleapp.main.R

class ListViewAdapter(
    private val context: Context,
    private val listViewArrayList: ArrayList<ListModel>): BaseAdapter() {

    override fun getCount(): Int {
        return listViewArrayList.count()
    }

    override fun getItem(position: Int): Any {
        return listViewArrayList[position]
    }

    override fun getItemId(position: Int): Long {
      return 0
    }
     override fun getView(position: Int, itemView: View?, viewGroup: ViewGroup): View {
         val inflater : LayoutInflater = LayoutInflater.from(context)
         val view : View = inflater.inflate(R.layout.list_item,null)

         val imageView = view.findViewById<ImageView>(R.id.image_view)
         val title = view.findViewById<TextView>(R.id.movie_name)

         imageView.setImageResource(listViewArrayList[position].getImage())
         title.text = listViewArrayList[position].getTitle()

         return view
     }
}