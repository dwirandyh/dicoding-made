package com.dwirandyh.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie

class MovieAdapter(private val movieList: ArrayList<Movie>, private val context: Context) :
    BaseAdapter() {


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false)
        }

        val viewHolder = MovieViewHolder(itemView as View)
        val movie = getItem(position)
        viewHolder.bind(movie as Movie)

        return itemView
    }

    override fun getItem(p0: Int): Any = movieList[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = movieList.size

    inner class MovieViewHolder(view: View) {
        private val ivLogo: ImageView = view.findViewById(R.id.iv_logo)
        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val tvDirector: TextView = view.findViewById(R.id.tv_director)
        private val tvTime: TextView = view.findViewById(R.id.tv_time)
        private val tvRating: TextView = view.findViewById(R.id.tv_rating)
        private val rbRating: RatingBar = view.findViewById(R.id.rb_rating)


        fun bind(item: Movie) {
            ivLogo.setImageDrawable(context.getDrawable(item.photo))
            tvName.text = item.name
            tvDirector.text = item.director
            tvTime.text = "${item.time} min"
            tvRating.text = item.rating.toString()
            rbRating.rating = item.ratingStar
        }
    }
}