package com.dwirandyh.movieapp.view.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.view.movie.MovieDetailFragmentArgs
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShow: TvShow = TvShowDetailFragmentArgs.fromBundle(arguments as Bundle).tvShow

        changeToolbarTitle(tvShow.name)

        tv_name.text = tvShow.name
        tv_rating.text = tvShow.rating.toString()
        rb_rating.rating = tvShow.ratingStar
        tv_description.text = tvShow.description

        loadPoster(tvShow.posterUrl)
    }

    private fun loadPoster(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_logo)
    }

    private fun changeToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}
