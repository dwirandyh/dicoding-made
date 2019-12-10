package com.dwirandyh.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Cast
import kotlinx.android.synthetic.main.item_actor.view.*

class ActorAdapter(private val actorList: List<Cast>) :
    RecyclerView.Adapter<ActorAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(actorList[position])
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Cast) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185${item.profilePath}")
                    .placeholder(R.drawable.ic_account_circle)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_profile)
            }
        }
    }
}