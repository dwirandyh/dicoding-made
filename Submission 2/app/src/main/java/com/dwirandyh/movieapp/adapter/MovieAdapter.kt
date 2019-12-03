package com.dwirandyh.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onItemClick: ((movie: Movie) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(item.posterUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_logo)

                tv_name.text = item.name
                tv_director.text = item.director
                tv_time.text = "${item.time} min"
                tv_rating.text = item.rating.toString()
                rb_rating.rating = item.ratingStar

                itemView.setOnClickListener {
                    onItemClick?.let {
                        it(item)
                    }
                }
            }
        }
    }
}