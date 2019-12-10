package com.dwirandyh.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.TvShow
import kotlinx.android.synthetic.main.item_movie_horizontal.view.*

class RecommendationTvShowAdapter(private val tvShowList: List<TvShow>) :
    RecyclerView.Adapter<RecommendationTvShowAdapter.MovieViewHolder>() {

    var onItemClick: ((tvShow: TvShow) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_horizontal, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TvShow) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185${item.posterPath}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_logo)

                tv_title.text = item.name

                itemView.setOnClickListener {
                    onItemClick?.let {
                        it(item)
                    }
                }
            }
        }
    }
}