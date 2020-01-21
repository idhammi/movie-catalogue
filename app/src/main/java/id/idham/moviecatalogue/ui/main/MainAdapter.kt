package id.idham.moviecatalogue.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.model.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by idhammi on 12/27/2019.
 */

class MainAdapter internal constructor(private val context: Context) : BaseAdapter() {

    internal var movies = arrayListOf<MovieModel>()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val movie = getItem(position) as MovieModel
        viewHolder.bind(movie)
        return itemView
    }

    override fun getItem(i: Int): Any = movies[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getCount(): Int = movies.size

    inner class ViewHolder constructor(private val view: View) {
        fun bind(movieModel: MovieModel) {
            with(view) {
                val name = "${movieModel.name} (${movieModel.year})"
                txt_name.text = name
                txt_description.text = movieModel.description
                Glide.with(context).load(movieModel.photo).into(img_photo)
            }
        }
    }

}