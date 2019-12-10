package com.dwirandyh.favoriteconsumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.favoriteconsumer.R
import com.dwirandyh.favoriteconsumer.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieList = ArrayList<Movie>()
        set(list) {
            this.movieList.clear()
            this.movieList.addAll(list)
            notifyDataSetChanged()
        }

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
                    .load("https://image.tmdb.org/t/p/w185${item.posterPath}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_logo)

                val rating = item.voteAverage / 2

                tv_name.text = item.title
                tv_vote_count.text = item.voteCount.toString()
                tv_popularity.text = item.popularity.toString()
                tv_rating.text = item.voteAverage.toString()
                rb_rating.rating = rating.toFloat()

                itemView.setOnClickListener {
                    onItemClick?.let {
                        it(item)
                    }
                }
            }
        }
    }
}